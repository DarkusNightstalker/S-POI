/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var Classifier = function () {

    var responsiveHelper_dt_basic = undefined;
    var breakpointDefinition = {
        tablet: 1024,
        phone: 480
    };
    return {
        create:function(){
            $("#form-search\\:create").click();
        },
        paginateResult: function (types) {
            var nSortable = new Array();
            for (var i = 1; i < types + 1; i++) {
                nSortable[i - 1] = i;
            }
            nSortable[types] = types + 2;
            $('#result-table').dataTable({
                "aoColumnDefs": [{
                        "visible": false,
                        "targets": 0
                    },
                    {
                        "bSortable": false,
                        "aTargets": nSortable
                    }],
                "order": [
                    [0, 'asc']
                ],
                "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'><'col-sm-6 col-xs-12 hidden-xs'l>r>" +
                        "t" +
                        "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
                "autoWidth": true,
                "oLanguage": {
                    "sLengthMenu": "_MENU_",
                    "sSearch": "<div class=\"input-group\">_INPUT_<span class=\"input-group-addon\"><i class=\"fa fa-search\"></i></span></div>",
                    "sInfo": "Mostrando <span class='txt-color-darken'>_START_</span> a <span class='txt-color-darken'>_END_</span> de <span class='text-primary'>_TOTAL_</span> registros",
                    "oPaginate": {
                        "sPrevious": "<i class=\"fa fa-arrow-left\"/>",
                        "sNext": "<i class=\"fa fa-arrow-right\"/>"
                    },
                    "sInfoEmpty": "",
                    "sInfoFiltered": " - buscado desde _MAX_ registro(s)",
                    "sZeroRecords": "No se han encontrado registros"
                }
            });
        },
        searcherConfigure: function () {
            $("#path").on("keypress", function (event) {
                if (event.charCode == 13) {
                    Classifier.search();
                    return false;
                }
            });
            $("#name").on("keypress", function (event) {
                if (event.charCode == 13) {
                    Classifier.search();
                    return false;
                }
            });
        },
        customReport: function () {
            $("#form-search\\:custom-report").click();
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#path").attr("disabled", "disabled");
            $("#name").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:path").val($("#path").val());
            $("#form-search\\:name").val($("#name").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#path").removeAttr("disabled");
            $("#name").removeAttr("disabled");
            $("#path").focus();
            var message = $("#message-search").val();
            if (message.length != 0) {

            }
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
                $("#path").val('');
                $("#name").val('');
                $("#form-search\\:path").val($("#path").val());
                $("#form-search\\:name").val($("#name").val());
                search();
            }
        },
        
        delete: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de eliminar el classificador '" + name + "'",
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
                content: "Esta seguro de restaurar el clasificador '" + name + "'",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    recovery();
                }
            });
        }
    };
}();
$(function () {
    Classifier.searcherConfigure();
});

