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
                    <#if myAttrs??>
                        <#list myAttrs as ma>
                            <div class="col-sm-6 col-lg-4">
                                <div class="card">
                                    <div class="card-header bg-success">
                                        <h4 style="text-align: center">我的属性/需求</h4>
                                        <ul class="card-actions">
                                            <li>
                                                <button type="button"><i class="mdi mdi-more"></i></button>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="card-body">
                                        <p style="text-align: center;">${ma}</p>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header"><h4>医生要求</h4></div>
                            <div class="card-body">
                                <form action="inputDemand" id="demand-add-form" method="post" class="row">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">我的需求</span>
                                        <input type="text" class="form-control must" id="word" name="word"  placeholder="请输入医生关键字" tips="请填写医生关键字" />
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
            if(!checkPatient("demand-add-form")){
                return;
            }
            var word = $("#word").val();
            $.ajax({
                url:'/docMatch/matdoctor',
                type:'POST',
                data:{word:word},
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('请求传输成功!',function(){
                            window.location.href = 'matchedDoctors';
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