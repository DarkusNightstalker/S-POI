/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var FunctionalSequence = function () {

    var responsiveHelper_dt_basic = undefined;
    var breakpointDefinition = {
        tablet: 1024,
        phone: 480
    };
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
                    "sZeroRecords": "No se han encontrado secuencias funcionales"
                }
            });
        },
        searcherConfigure: function () {
            $("#code").on("keypress", function (event) {
                if (event.charCode == 13) {
                    FunctionalSequence.search();
                    return false;
                }
            });
            $("#name").on("keypress", function (event) {
                if (event.charCode == 13) {
                    FunctionalSequence.search();
                    return false;
                }
            });
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#code").attr("disabled", "disabled");
            $("#name").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:code").val($("#code").val());
            $("#form-search\\:name").val($("#name").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#code").removeAttr("disabled");
            $("#name").removeAttr("disabled");
            $("#code").focus();
            var message = $("#message-search").val();
            if (message.length != 0) {

            }
        }
    };
}();
$(function () {
    FunctionalSequence.searcherConfigure();
});
