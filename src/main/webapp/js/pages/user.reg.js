/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var User = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        delete: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de eliminar el usuario '" + name + "'",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    del();
                }
            });
        },
        paginateResult: function () {
            $('#result-table').dataTable({
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [3]
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
                    "sZeroRecords": "No se han encontrado usuarios"
                }
            });
        },
        searcherConfigure: function () {
            $("#username").on("keypress", function (event) {
                if (event.charCode == 13) {
                    User.search();
                    return false;
                }
            });
            $("#full-name").on("keypress", function (event) {
                if (event.charCode == 13) {
                    User.search();
                    return false;
                }
            });
            $("#dependency").select2({
                width:"100%",
                placeholder:"Cualquier dependencia"
            });
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#username").attr("disabled", "disabled");
            $("#full-name").attr("disabled", "disabled");
            $("#dependency").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:username").val($("#username").val());
            $("#form-search\\:full-name").val($("#full-name").val());
            $("#form-search\\:dependency").val($("#dependency").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#username").removeAttr("disabled");
            $("#full-name").removeAttr("disabled");
            $("#dependency").removeAttr("disabled");
            $("#username").focus();
            var message = $("#message-search").val();
            if (message.length != 0) {

            }
        }
    };
}();
$(function () {
    User.searcherConfigure();
});