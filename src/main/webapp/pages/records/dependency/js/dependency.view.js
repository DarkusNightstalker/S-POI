/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var Dependency = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        searcherConfigure: function () {
            $("#path").on("keypress", function (event) {
                if (event.charCode == 13) {
                    Dependency.search();
                    return false;
                }
            });
            $("#name").on("keypress", function (event) {
                if (event.charCode == 13) {
                    Dependency.search();
                    return false;
                }
            });
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#path").attr("disabled", "disabled");
            $("#name").attr("disabled", "disabled");
            $("#operational").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:path").val($("#path").val());
            $("#form-search\\:name").val($("#name").val());
            $("#form-search\\:operational").val($("#operational").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#path").removeAttr("disabled");
            $("#name").removeAttr("disabled");
            $("#operational").removeAttr("disabled");
            $("#path").focus();
        },
        delete: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de eliminar la dependencia de tareas '" + name + "'",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    del();
                }
            });
        },
        recovery: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de restaurar la dependencia de tareas '" + name + "'",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    recovery();
                }
            });
        },
        updateProgress: function () {
            $("#import-state").html($("#form-progress\\:state").val());
            $("#import-percent").html($("#form-progress\\:percent").val());
            $("#form-import .progress-bar").css("width" , $("#form-progress\\:percent").val()+"%");
            $("#form-import\\:load-wrapper table tbody").append($("#form-progress\\:content").val());
            
            var objDiv = document.getElementById("import-data");
            objDiv.scrollTop = objDiv.scrollHeight;
            if ($("#form-progress\\:finalize").val() != "true") {
                updateProgress();
            } else {
                $("#code").val('');
                $("#name").val('');
                $("#form-search\\:code").val($("#code").val());
                $("#form-search\\:name").val($("#name").val());
                search();
            }
        }
    };
}();
$(function () {
    Dependency.searcherConfigure();
});
