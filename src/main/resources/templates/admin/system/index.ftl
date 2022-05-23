<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!"header is null"}</title>
    <#include "../common/header.ftl"/>
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <#include "../common/left-menu.ftl"/>
        <#include "../common/header-menu.ftl"/>

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">
                <#include "../common/second-header.ftl"/>
                <div class="row">

                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header" style="text-align: center">
                                <h4 style="font-weight: bold;font-size: large">项目信息</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>操作人</th>
                                            <th style="width:400px;">操作内容</th>
                                            <th>操作时间</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <#if operatorLogs??>
                                                <#list operatorLogs as opetatorLog>
                                                    <tr>
                                                    <td>${opetatorLog_index+1}</td>
                                                    <td>${opetatorLog.getOperater()}</td>
                                                    <td>${opetatorLog.content}</td>
                                                    <td>${opetatorLog.createTime}</td>
                                                    </tr>
                                                </#list>

                                            </#if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
</body>
</html>
