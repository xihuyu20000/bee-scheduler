define(['text!comp/spider-edit.html', 'css!./spider-edit.css'], function (tpl) {
    var aceEditor = function (resolver) {
        require(['comp/ace-editor'], resolver);
    };

    return {
        template: tpl,
        components: {
            "ace-editor": aceEditor
        },
        data: function () {
            var vm = this;
            var editFor = vm.$route.meta.editFor;

            var validators = {
                taskName: [
                    {required: true, message: '请输入爬虫名称', trigger: 'blur'}
                ],
                taskGroup: [
                    {required: true, message: '请输入爬虫所属组', trigger: 'blur'}
                ]
            };

            var data = {
                editFor: editFor,
                validators: validators,
                taskGroupList: [],
                postTaskInProcess: false,
                initEditFormModelInProcess: false,
                editSpiderFormModel: {
                	id: '',
                    name: '',
                    group: 'Default',
                    params: '',
                    description: ''
                },
                editSpiderFormModelInitBackup: null
            };

            vm.$http.get("/task/groups").then(function (re) {
                var taskGroupList = [];
                var reData = re.body;
                for (var i = 0; i < reData.length; i++) {
                    taskGroupList.push({value: reData[i]});
                }
                data.taskGroupList = taskGroupList;
            });

            return data;
        },
        watch: {
            'editSpiderFormModel.taskModule': function (newVal, oldVal) {
                var selectedtaskModule = this.taskModuleList[newVal];
                if (this.editFor === "New") {
                    this.editSpiderFormModel.params = selectedtaskModule.paramTemplate;
                } else if (this.editFor === "Copy") {
                    if (this.editSpiderFormModelInitBackup !== null && newVal === this.editSpiderFormModelInitBackup.taskModule) {
                        this.editSpiderFormModel.params = this.editSpiderFormModelInitBackup.params;
                    } else {
                        this.editSpiderFormModel.params = selectedtaskModule.paramTemplate;
                    }
                }
            }
        },
        mounted: function () {
        	var vm = this;
            var editFor = vm.$route.meta.editFor;
            if (editFor === "Edit" || editFor === "Copy" ){
            	vm.editSpiderFormModel =vm.$route.query;
            }
            if (editFor === "Copy" ){
            	vm.editSpiderFormModel.id = '';
            }
        },
        methods: {
            queryTaskGroup: function (q, cb) {
                var taskGroupList = this.taskGroupList;
                var results = q ? taskGroupList.filter(this.createTaskGroupFilter(q)) : taskGroupList;
                cb(results);
            },
            createTaskGroupFilter: function (q) {
                return function (item) {
                    return (item.value.toLocaleLowerCase().indexOf(q.toLowerCase()) === 0);
                };
            },
            routerGoback: function () {
                this.$router.go(-1);
            },
            postTask: function () {
                var vm = this;

                vm.$refs["editSpiderForm"].validate(function (valid) {
                    if (valid) {
                        var editSpiderFormModel = vm.editSpiderFormModel;
                        var editFor = vm.editFor;
                        var postUrl = editFor === "New" ? "/spider/new" : editFor === "Copy" ? "/spider/new" : "/spider/edit";
                        vm.postTaskInProcess = true;
                        console.log(editSpiderFormModel);
                        vm.$http.post(postUrl, editSpiderFormModel).then(function (re) {
                            vm.$message({message: '保存成功！', type: 'success'});
                            vm.postTaskInProcess = false;
                            vm.routerGoback();
                        }, function () {
                            vm.postTaskInProcess = false;
                        });
                    } else {
                        vm.$message({message: '填写有误，请检查', type: 'warning'});
                        return false;
                    }
                });
            }
        }
    };
});