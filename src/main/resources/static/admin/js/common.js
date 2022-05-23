//显示成功信息
function showSuccessMsg(msg,callback){
    $.confirm({
        title: '成功',
        content: msg,
        type: 'green',
        typeAnimated: false,
        buttons: {
            omg: {
                text: '确定！',
                btnClass: 'btn-green',
                action: function(){
                    callback();
                }
            }
        }
    });
}
//显示错误信息
function showErrorMsg(msg){
    $.confirm({
        title: '错误',
        content: msg,
        type: 'red',
        typeAnimated: false,
        buttons: {
            omg: {
                text: '知道了！',
                btnClass: 'btn-red',
                action: function(){

                }
            }
        }
    });
}
//显示警告信息
function showWarningMsg(msg){
    $.confirm({
        title: '警告',
        content: msg,
        type: 'green',
        typeAnimated: false,
        buttons: {
            omg: {
                text: '确认',
                btnClass: 'btn-green',
                action: function(){

                }
            }
        }
    });
}
//表单验证公用方法，传表单form的id进来即可
function checkForm(formId){
    var flag = true;
    $("#"+formId).find(".required").each(function(i,e){
        if($(e).val() == ''){
            showWarningMsg($(e).attr('tips'));
            flag = false;
            return false;
        }
    });
    return flag;
}
//表单验证公用方法，传表单form的id进来即可
function checkPatient(formId){
    var flag = true;
    $("#"+formId).find(".must").each(function(i,e){
        if($(e).val() == ''){
            showWarningMsg($(e).attr('tips'));
            flag = false;
            return false;
        }
    });
    return flag;
}