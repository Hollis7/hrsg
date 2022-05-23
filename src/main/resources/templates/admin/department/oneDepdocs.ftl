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
                            <div class="card-header" >
                                <h4 style="font-weight: bold;font-size: large;text-align: left">医院信息</h4>
                                <div class="col-lg-6">
                                    <div class="input-group">
                                        <select name="certain_dep" class="form-control" id="certain_dep" >
                                            <#if departments??>
                                                <#list departments as deps>
                                                    <option value="${deps.id}" style="font-weight:bold;">${deps.dep_name}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                        <span class="input-group-btn" >
                                          <button class="btn btn-default" type="button" id="search-confirm-btn" >搜索</button>
                                        </span>
                                    </div>
                                </div>

                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>医生姓名</th>
                                            <th>医生性别</th>
                                            <th style="width:50px;">医生主治</th>
                                            <th style="width:400px;">医生介绍</th>
                                            <th>医生等级</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#if doctors??>
                                            <#list doctors as doc>
                                                <tr>
                                                    <td style="vertical-align:middle;">${doc.doc_name}</td>
                                                    <td style="vertical-align:middle;">
                                                        <#switch doc.doc_gender>
                                                            <#case 1> 男 <#break>
                                                            <#case 2>女<#break>
                                                            <#case 0>unknown<#break>
                                                        </#switch>
                                                    </td>
                                                    <td style="vertical-align:middle;">${doc.doc_major}</td>
                                                    <td style="vertical-align:middle;">${doc.doc_introduction}</td>
                                                    <td style="vertical-align:middle;">${doc.doc_level}</td>
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
<script type="text/javascript">
    $(document).ready(function (){
        $("#search-confirm-btn").click(function (){
            var id=$("#certain_dep").val();
            $.ajax({
                url:'selectDep',
                type:'POST',
                data: {id:id},
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('科室跳转成功!',function(){
                            window.location.href = 'oneDepartment';
                        })
                    }else{
                        showErrorMsg(data.msg);
                    }
                },
                error:function(data){
                    alert('网络错误!');
                }
            });
        });
    });
</script>
</body>
</html>
