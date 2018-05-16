/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var SpecificGoal = function () {
    return {
        save: function () {
            if ($("#form").valid()) {
                save();
            }
        },
        paginateABPSearch: function () {
            $('#form-abp table').dataTable({
                "sDom": "f" +
                        "t" ,
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [0]
                    }],
                "order": [
                    [1, 'asc']
                ],
                "autoWidth": true,
                "bPaginate": false,
                "oLanguage": {
                    "sLengthMenu": "_MENU_",
                    "sSearch": "<div class=\"search-dt-input hidden\">_INPUT_</div>",
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
        paginateDependencySearch: function () {
            $('#form-dependency table').dataTable({
                "sDom": "f" +
                        "t" ,
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [0]
                    }],
                "order": [
                    [1, 'asc']
                ],
                "autoWidth": true,
                "bPaginate": false,
                "oLanguage": {
                    "sLengthMenu": "_MENU_",
                    "sSearch": "<div class=\"search-dt-input hidden\">_INPUT_</div>",
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
        back: function () {
            if ($("#form\\:code").val() != "" || $("#form\\:name").val() != "" || $("#form\\:strategic-plan").val() != "") {
                $.SmartMessageBox({
                    title: "Aviso!",
                    content: "Esta seguro de salir? (Se perderan todos los cambios realizados)",
                    buttons: '[No][Si]'
                }, function (ButtonPressed) {
                    if (ButtonPressed === "Si") {
                        $("#form\\:back").trigger("click");
                    }
                });
            } else {
                $("#form\\:back").trigger("click");
            }
        }
    };
}();

$(function () {

    $('#form\\:strategic-plan').select2({
        placeholder: 'Seleccione un plan estrategico',
        width: '100%'
    });
    $("#form").validate({
        
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        
        rules: {
            "form:code": {
                required: true
            },
            "form:name": {
                required: true
            },
            "form:strategic-plan": {
                required: true
            },
            "form:strategic-axis": {
                required: true
            }
        },
        
        messages: {
            "form:code": {
                required: 'Por favor ingrese un codigo'
            },
            "form:name": {
                required: 'Por favor ingrese un nombre'
            },
            "form:strategic-plan": {
                required: 'Por favor seleccione un plan estrategico'
            },
            "form:strategic-axis": {
                required: 'Por favor seleccione un eje estrategico'
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').addClass(errorClass).removeClass(validClass);
            if ($(element).hasClass("select2-offscreen")) {
                $("#s2id_" + $(element).attr("id").replace(":", "\\:") + " ul").addClass(errorClass);
            } else {
                $(element).addClass(errorClass);
            }
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').removeClass(errorClass).addClass(validClass);
            if ($(element).hasClass("select2-offscreen")) {
                $("#s2id_" + $(element).attr("id").replace(":", "\\:") + " ul").removeClass(errorClass).addClass(validClass);
            } else {
                $(element).removeClass(errorClass);
            }
        },
        errorPlacement: function (error, element) {
            error.insertAfter(element);
        }
    });
});

