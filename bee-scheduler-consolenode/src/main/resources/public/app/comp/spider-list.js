define(['text!comp/spider-list.html', 'css!./spider-list.css'], function (tpl) {

    var comp_task_trends_plate = function (resolver) {
        require(['comp/task-trends-plate'], resolver);
    };

    return {
        template: tpl,
        components: {
            "task-trends-plate": comp_task_trends_plate
        },
        data: function () {
            var vm = this;
            var data = {
                quickTaskDialogVisible: false,
                queryLoading: false,
                queryParams: {
                    keyword: '',
                    page: 1
                },
                curQueryParams: null,
                queryResult: {},
                selectedItems: [],
                enabledCommandBtn: [],
                taskModuleList: {}
            };

            return data;
        },
        computed: {
            showResumeBtn: function () {
                for (var i = 0; i < this.selectedItems.length; i++) {
                    var item = this.selectedItems[i];
                    if (item.state === "PAUSED" || item.state === "PAUSED_BLOCKED") {
                        return true;
                    }
                }
                return false;
            },
            showCommandBtnGroup: function () {
                return this.selectedItems.length > 0;
            }
        },
        mounted: function () {
            this.query();
        },
        methods: {
            query: function () {
                var vm = this;
                vm.queryParams.page = 1;
                vm.load(vm.queryParams);
            },
            load: function (queryParams) {
                var vm = this;

                vm.curQueryParams = queryParams;
                vm.queryLoading = true;
                vm.queryResult = {};
                vm.selectedItems = [];

                vm.$http.get("/spider/list", {params: queryParams}).then(function (re) {
                    vm.queryLoading = false;
                    vm.queryResult = re.body;
                }, function () {
                    vm.queryLoading = false;
                    vm.queryResult = {};
                });
            },
            reload: function () {
                this.load(this.curQueryParams);
            },
            changePage: function (val) {
                this.curQueryParams.page = val;
                this.load(this.curQueryParams);
            },
            querySuggestion: function (queryString, callback) {
                var vm = this;
                var suggestions = [];
                vm.$http.get("/task/query-suggestions", {params: {input: queryString}}).then(function (re) {
                    var suggestionResult = re.body;
                    suggestionResult.forEach(function (value) {
                        suggestions.push({"value": value});
                    });
                    callback(suggestions);
                }, function (reason) {
                    callback(suggestions);
                });
            },
            onRowClick: function (row, event, column) {
                console.log(column.id);
                if (column.id !== "el-table_1_column_1") {
                    this.$taskDetailDialog.open(row.name, row.group);
                }
            },
            pauseTask: function (name, group) {
                var vm = this;
                var taskIds = [];
                for (var i = 0; i < vm.selectedItems.length; i++) {
                    var item = vm.selectedItems[i];
                    taskIds.push(item.group + "-" + item.name);
                }

                vm.$confirm("确认暂停选中的任务?", '提示', {type: 'warning'}).then(function () {
                    vm.$http.post("/task/pause", null, {params: {taskIds: taskIds.join(",")}}).then(function (re) {
                        vm.$message({message: '任务已暂停', type: 'success'});
                        vm.reload();
                    });
                }).catch(function () {
                    //...
                });
            },
            executeSpider: function (name, group) {
                var vm = this;
                if (vm.selectedItems.length != 1) {
                	vm.$message({message: '请只勾选一个爬虫', type: 'warning'});
                	return;
                }

                let editTaskFormModel = {
                    name: this.$moment().format("YYYYMMDDHHmmssSSS"),
                    taskModule: "SpiderTaskModule",
                    params: "{'spider':'"+vm.selectedItems[0].id+"'}",
                    enableStartDelay: false,
                    startDelay: 1000
                };
                vm.postTaskInProcess = true;
                vm.$http.post("/spider/tmp", editTaskFormModel).then(function (re) {
                    vm.$message({message: '任务已触发！', type: 'success'});
                    vm.postTaskInProcess = false;
                    vm.visible = false;
                }, function () {
                    vm.postTaskInProcess = false;
                });
            },
            deleteSpider: function (name, group) {
                var vm = this;
                var spiderIds = [];
                for (var i = 0; i < vm.selectedItems.length; i++) {
                    var item = vm.selectedItems[i];
                    spiderIds.push(item.id);
                }

                vm.$confirm("确认删除选中的爬虫?", '提示', {type: 'warning'}).then(function () {
                    vm.$http.post("/spider/delete", null, {params: {spiderIds: spiderIds.join(",")}}).then(function (re) {
                        vm.$message({message: '爬虫已删除', type: 'success'});
                        vm.reload();
                    });
                }).catch(function () {
                    //...
                });
            },
            handleSelectionChange: function (val) {
                this.selectedItems = val;
            },
            createQuickTask: function () {
                this.quickTaskDialogVisible = true
            },
            goTaskExecHistoryList: function (group, name) {
                this.$router.push("/task/history/list/" + encodeURI("g:" + group + " " + name));
            },
            goCreateSpider: function () {
                this.$router.push("/spider/new");
            },
            goCopySpider: function () {
                var target = this.selectedItems[0];
                this.$router.push({path:"/spider/copy/", query:target});
            },
            goEditSpider: function () {
                var target = this.selectedItems[0];
                this.$router.push({path:"/spider/edit/", query:target});
            }
        }
    };
});