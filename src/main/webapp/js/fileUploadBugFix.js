function clearInvalidFileMsg(target){
    target.uploadContent.find("tr.ui-state-error").remove();
}

$(document).ready(function(){
    fileupload_wgt.buttonBar.find("button.cancel").bind("click", function(e){
        clearInvalidFileMsg(fileupload_wgt);
    });

    fileupload_wgt2.buttonBar.find("button.cancel").bind("click", function(e){
        clearInvalidFileMsg(fileupload_wgt2);
    });

});
