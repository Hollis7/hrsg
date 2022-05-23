<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}${title!""}</title>
    <#include "../common/header.ftl"/>

</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="!#"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}" alt="${siteName!""}" /></a>
            </div>
            <div class="lyear-layout-sidebar-scroll">
                <#include "../common/left-menu.ftl"/>
            </div>

        </aside>
        <!--End 左侧导航-->

        <#include "../common/header-menu.ftl"/>

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header"><h4>个人信息</h4></div>
                            <div class="card-body">
                                <form action="addPatient" id="patient_add_form" method="post" class="row">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">姓名</span>
                                        <input type="text" readonly="readonly" class="form-control must" id="username" name="username" value=${myname!""} placeholder="请输入姓名" tips="请填写姓名" />
                                    </div>
<#--                                    <div class="input-group m-b-10">-->
<#--                                        <span class="input-group-addon">性别</span>-->
<#--                                        <input type="number" class="form-control must" id="sex" name="sex" value="" placeholder="请填写性别，男为1，女为2" tips="请填写性别" />-->
<#--                                    </div>-->
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">性别</span>
                                        <select name="sex" class="form-control" id="type">
                                            <option value=1>男</option>
                                            <option value=2>女</option>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">年龄</span>
                                        <input type="number" class="form-control must" id="age" name="age" value="" placeholder="请填写年龄" tips="请填写年龄" />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">病情</span>
                                        <textarea type="text" class="form-control must" id="medical_record" name="medical_record" value="" rows="5"  placeholder="请填写病情" tips="请填写病情"></textarea>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post" id="add-form-submit-btn">确 定</button>
                                        <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                    </div>
                                </form>

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
<script type="text/javascript" src="/admin/js/common.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        //提交按钮监听事件
        $("#add-form-submit-btn").click(function(){
            if(!checkPatient("patient_add_form")){
                return;
            }
            var data = $("#patient_add_form").serialize();
            $.ajax({
                url:'addPatient',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('病历添加成功!',function(){
                            window.location.href = 'person';
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