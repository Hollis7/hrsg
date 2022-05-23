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
                                <h4 style="font-weight: bold;font-size: large">欢迎使用</h4>
                            </div>
                            <div style="font-size: larger;text-indent: 4em;color:green;">
                                <p class="lead">欢迎使用具有隐私保护的医疗预约挂号系统，我们将为您提供最便捷的预约挂号，最安全的隐私保护，最优质的医疗服务，为您选择最合适您的医生。</p>
                                <p class="lead">我们医院有四大科室，分别是：皮肤科、外科、内科、妇科，具有强大的医疗团队配置。在医学上我们是专业的，在在您的隐私保护上我们也是专业。</p>
                                <p class="lead">我们将把您的病历加密存储到云端，对您的搜索进行隐藏，您再也不用担心病历泄露，操作敏感被不法分子分析监听了，请放心使用本系统，我们将为您全程保驾护航。</p>
                                <p class="lead">最后，请您按照预约操作导航进行预约挂号，祝您早日恢复健康。</p>
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
