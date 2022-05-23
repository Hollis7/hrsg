<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>我的科室-初步匹配</title>
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
                            <div class="card-header" >
                                <h4 style="font-weight: bold;font-size: large;text-align: center">科室匹配结果</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>科室编号</th>
                                            <th>科室名称</th>
                                            <th style="width:400px;">科室介绍</th>
                                            <th style="width:50px;">科室主治</th>
                                            <th>科室医生</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#if matchedDeps??>
                                            <#list matchedDeps as department>
                                                <tr>
                                                    <td style="vertical-align:middle;">${department_index+1}</td>
                                                    <td style="vertical-align:middle;">${department.getDep_name()}</td>
                                                    <td style="vertical-align:middle;">${department.getDep_introduce()}</td>
                                                    <td style="vertical-align:middle;">${department.getDep_major_key()}</td>
                                                    <td style="vertical-align:middle;">${department.getDep_doctor()}</td>
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
