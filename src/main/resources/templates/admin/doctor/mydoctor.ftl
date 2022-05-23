<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>我的科室-医生匹配</title>
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
                                <h4 style="font-weight: bold;font-size: large;text-align: center">医生匹配结果</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>
                                                <label class="lyear-checkbox checkbox-primary">
                                                    <input style="vertical-align:middle;" type="checkbox" id="check-all"><span></span>
                                                </label>
                                            </th>
                                            <th style="width:100px;">医生姓名</th>
                                            <th>科室名称</th>
                                            <th>医生职称</th>
                                            <th>科室主治</th>
                                            <th style="width:600px;">医生介绍</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#if mydoctor??>
                                            <#list mydoctor as md>
                                                <tr>
                                                    <td>
                                                        <label class="lyear-checkbox checkbox-primary">
                                                            <input style="vertical-align:middle;" type="checkbox" name="ids[]" value="${md.getId()}"><span></span>
                                                        </label>
                                                    </td>
                                                    <td style="vertical-align:middle;">${md.getDoc_name()}</td>
                                                    <td style="vertical-align:middle;">${md.getDepartment().getDep_name()}</td>
                                                    <td style="vertical-align:middle;">${md.getDoc_level()}</td>
                                                    <td style="vertical-align:middle;">${md.getDoc_major()}</td>
                                                    <td style="vertical-align:middle;">${md.getDoc_introduction()}</td>
                                                </tr>
                                            </#list>

                                        </#if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="form-group col-md-12" style="text-align: right">
                                <button type="button" class="btn btn-primary ajax-post" id="select-form-submit-btn">确 定</button>
                                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
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
    $(document).ready(function(){
        //提交按钮监听事件
        $("#select-form-submit-btn").click(function(){
            $.ajax({
                url:'/docMatch/selectDoc',
                type:'POST',
                data:{doc_id:getDoc()},
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('请求传输成功!',function(){
                            window.location.href = 'myAppointment';
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
    function getDoc() {
        var str = "";
        $("input[name='ids[]']").each(function() {
            if($(this).prop("checked") == true) {
                str += ($(this).val() + "#")
            }
        });
        return str;
    }
</script>
</body>
</html>
