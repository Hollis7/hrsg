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
                            <div class="card-header" ><h4 style="font-weight: bold;font-size: large;text-align: left">个人病历</h4></div>
                                <div class="card-body">
                                    <dl class="row" >
                                        <#if person??>
                                            <dt class="col-sm-3">姓名</dt>
                                            <dd class="col-sm-9">${person.getUsername()!""}</dd>

                                            <dt class="col-sm-3">年龄</dt>
                                            <dd class="col-sm-9">${person.getAge()!""}</dd>

                                            <dt class="col-sm-3">性别</dt>
                                            <dd class="col-sm-9">
                                              <#switch person.getSex()>
                                                  <#case 1> 男 <#break>
                                                  <#case 2>女<#break>
                                                  <#case 0>unknown<#break>
                                              </#switch>
                                            </dd>

                                            <dt class="col-sm-3 text-truncate">病历</dt>
                                            <dd class="col-sm-9" ><p>${person.medical_record!"这是空的"}</p></dd>

                                            <dt class="col-sm-3">更新时间</dt>
                                            <dd class="col-sm-9">${person.updateTime}</dd>
                                        </#if>
                                </dl>

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
        $("#add-form-submit-btn").click(function(){
            if(!checkForm("patient_add_form")){
                return;
            }
            var data = $("#patient_add_form").serialize();
            $.ajax({
                url:'add',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('菜单添加成功!',function(){
                            window.location.href = '../department/list';
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
    function getSelectedIcon(){
        $("#icon").val($(".selected-icon").attr('val'));
        $("#icons-panel").modal('hide');
    }
</script>
</body>
</html>