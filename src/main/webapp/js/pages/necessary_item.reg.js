/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var NecessaryItem = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        paginateResult: function () {
            $('#result-table').dataTable({
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [3]
                    }],
                "order": [
                    [0, 'asc'],
                    [1, 'asc']
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
                    "sZeroRecords": "No se han encontrado rubros"
                }
            });
        },
        searcherConfigure: function () {
            $("#code").on("keypress", function (event) {
                if (event.charCode == 13) {
                    NecessaryItem.search();
                    return false;
                }
            });
            $("#name").on("keypress", function (event) {
                if (event.charCode == 13) {
                    NecessaryItem.search();
                    return false;
                }
            });
            $("#generic-classifier").select2({
                width: '100%',
                placeholder : 'Cualquier tipo'
            })
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#code").attr("disabled", "disabled");
            $("#name").attr("disabled", "disabled");
            $("#generic-classifier").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:code").val($("#code").val());
            $("#form-search\\:name").val($("#name").val());
            $("#form-search\\:generic-classifier").val($("#generic-classifier").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#code").removeAttr("disabled");
            $("#name").removeAttr("disabled");
            $("#generic-classifier").removeAttr("disabled");
            $("#code").focus();
            var message = $("#message-search").val();
            if (message.length != 0) {

            }
        },
        delete: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de eliminar la fte. financ. '" + name + "'",
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
                content: "Esta seguro de restaurar la fte. financ. '" + name + "'",
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
    NecessaryItem.searcherConfigure();
});
