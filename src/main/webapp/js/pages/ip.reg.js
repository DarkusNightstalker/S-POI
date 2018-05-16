var IP = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        paginateResult: function () {
            $('#result-table').dataTable({
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [2]
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
                    "sZeroRecords": "No se han encontrado rubros"
                }
            });
        },
        searcherConfigure: function () {
            $("#dependency").on("keypress", function (event) {
                if (event.charCode == 13) {
                    IP.search();
                    return false;
                }
            });
            $("#name").on("keypress", function (event) {
                if (event.charCode == 13) {
                    IP.search();
                    return false;
                }
            });
        },
        
        delete: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de eliminar el programa presupuestal '" + name + "'",
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
                content: "Esta seguro de restaurar el programa presupuestal '" + name + "'",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    recovery();
                }
            });
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#dependency").attr("disabled", "disabled");
            $("#name").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:dependency").val($("#dependency").val());
            $("#form-search\\:name").val($("#name").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#dependency").removeAttr("disabled");
            $("#name").removeAttr("disabled");
            $("#code").focus();
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
    IP.searcherConfigure();
});
