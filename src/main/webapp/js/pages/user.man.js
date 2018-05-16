/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var User = function () {
    function validateEmail(email) {
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }
    return {
        save: function () {
            if ($("#form").valid()) {
                try {
                    submitRols();
                } catch (Exception) {
                    $.smallBox({
                        title: "ERROR!",
                        content: 'No se ha podido completar la subida',
                        color: "#C46A69",
                        timeout: 5000,
                        icon: "fa fa-warning shake animated"
                    });
                }
            }
        },
        searchByDni: function () {
            var dni = $.trim($("#form\\:dni").val());
            if (dni.length != 8) {
                $.smallBox({
                    title: "DNI invalido",
                    content: 'El dni  consta de 8 digitos!!',
                    color: "#C46A69",
                    timeout: 5000,
                    icon: "fa fa-warning shake animated"
                });
                return;
            } else {
                searchByDni();
            }
        },
        inputDni: function () {
//            removePerson();
        },
        endSavePerson: function () {
            if ($("#form-person\\:success-save").val() == "true") {
                $('#modal-person').modal('toggle');
            }
        },
        paginateRolSearch: function () {
            $('#form-rols table').dataTable({
                "sDom": "f" +
                        "t",
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
        generate: function () {
            var name = $.trim($("#form\\:name").val());
            var pattern = $.trim($("#form\\:pattern").val());
            if (name.length != 0 && pattern.length != 0) {
                generate();
            } else {
                $("#username").html("");
                $("#password").html("");
            }
        },
        configureGenerate: function () {
            $("#form\\:name").on("input", function () {
                User.generate();
            });
            $("#form\\:pattern").on("input", function () {
                User.generate();
            });
        },
        beginSearchByDni: function () {
            $("#form\\:dni").prop("readonly", true);
            $("#form\\:dni").parent().find("i").removeClass("hidden");
        },
        endSearchByDni: function () {
            $("#form\\:dni").prop("readonly", false);
            $("#form\\:dni").parent().find("i").addClass("hidden");
        },
        back: function () {
            if ($("#code").val() != "" || $("#name").val() != "") {
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
        },
        configure_dni: function () {
            $('#dni').editable({
                url: '/post',
                showbuttons:false,
                type: 'text',
                mode: 'inline',
                pk: 1,
                name: 'dni',
                title: 'Ingrese dni',
                emptytext: '<em>Ninguno</em>',
                validate: function (value) {
                    if ($.trim(value) == '') {
                        return 'Este campo es obligatorio';
                    } else if (value.length != 8) {
                        return 'El dni debe tener 8 digitos';
                    }
                },
                success: function (response, newValue) {
                }
            });
        },
        configure_name: function () {
            $('#name').editable({
                url: '/post',
                type: 'text',
                showbuttons:false,
                mode: 'inline',
                pk: 1,
                name: 'name',
                title: 'Ingrese Nombre',
                emptytext: '<em>Ninguno</em>',
                validate: function (value) {
                    if ($.trim(value) == '') {
                        return 'Este campo es obligatorio';
                    }
                },
                success: function (response, newValue) {
                    $("#form\\:name").val(newValue);
                    updatePersonalData();
                }
            });
        },
        configure_email: function () {
            $('#email').editable({
                url: '/post',
                type: 'text',
                showbuttons:false,
                mode: 'inline',
                pk: 1,
                name: 'email',
                title: 'Ingrese e-mail',
                emptytext: '<em>Ninguno</em>',
                success: function (response, newValue) {
                    $("#form\\:email").val(newValue);
                    updatePersonalData();
                }
            });
        },
        configure_lastname: function () {
            $('#pattern').editable({
                url: '/post',
                type: 'text',
                showbuttons:false,
                mode: 'inline',
                pk: 1,
                name: 'pattern',
                title: 'Ingrese Apellido paterno',
                emptytext: '<em>Ninguno</em>',
                validate: function (value) {
                    if ($.trim(value) == '') {
                        return 'Este campo es obligatorio';
                    }
                },
                success: function (response, newValue) {
                    $("#form\\:pattern").val(newValue);
                    updatePersonalData();
                }
            });
            $('#mattern').editable({
                url: '/post',
                type: 'text',
                showbuttons:false,
                mode: 'inline',
                pk: 1,
                name: 'name',
                title: 'Ingrese Apellido materno',
                emptytext: '<em>Ninguno</em>',
                validate: function (value) {
                    if ($.trim(value) == '') {
                        return 'Este campo es obligatorio';
                    }
                },
                success: function (response, newValue) {
                    $("#form\\:mattern").val(newValue);
                    updatePersonalData();
                }
            });
        }
    };
}();

$(function () {
    (function (e) {
        "use strict";
        var t = function (e) {
            this.init("address", e, t.defaults)
        };
        e.fn.editableutils.inherit(t, e.fn.editabletypes.abstractinput);
        e.extend(t.prototype, {
            render: function () {
                this.$input = this.$tpl.find("input")
            },
            value2html: function (t, n) {
                if (!t) {
                    e(n).empty();
                    return
                }
                var r = e("<div>").text(t.city).html() + ", " + e("<div>").text(t.street).html() +
                        " st., bld. " + e("<div>").text(t.building).html();
                e(n).html(r)
            },
            html2value: function (e) {
                return null
            },
            value2str: function (e) {
                var t = "";
                if (e)
                    for (var n in e)
                        t = t + n + ":" + e[n] + ";";
                return t
            },
            str2value: function (e) {
                return e
            },
            value2input: function (e) {
                if (!e)
                    return;
                this.$input.filter('[name="city"]').val(e.city);
                this.$input.filter('[name="street"]').val(e.street);
                this.$input.filter('[name="building"]').val(e.building)
            },
            input2value: function () {
                return {
                    city: this.$input.filter('[name="city"]').val(),
                    street: this.$input.filter('[name="street"]').val(),
                    building: this.$input.filter('[name="building"]').val()
                }
            },
            activate: function () {
                this.$input.filter('[name="city"]').focus()
            },
            autosubmit: function () {
                this.$input.keydown(function (t) {
                    t.which === 13 && e(this).closest("form").submit()
                })
            }
        });
        t.defaults = e.extend({}, e.fn.editabletypes.abstractinput.defaults, {
            tpl: '<div class="editable-address"><label><span>City: </span><input type="text" name="city" class="input-small"></label></div><div class="editable-address"><label><span>Street: </span><input type="text" name="street" class="input-small"></label></div><div class="editable-address"><label><span>Building: </span><input type="text" name="building" class="input-mini"></label></div>',
            inputclass: ""
        });
        e.fn.editabletypes.address = t
    })(window.jQuery);
    
    $.mockjaxSettings.responseTime = 500;
    
    $.mockjax({
        url: '/post',
        response: function (settings) {
        }
    });
    
    $("#form\\:dni").on("keypress", function (event) {
        if (event.charCode == 13) {
            User.searchByDni();
            return false;
        }
    }).on("input", function () {
        User.inputDni();
    });
    User.configureGenerate();
    $("#form").validate({
        /* @validation states + elements 
         ------------------------------------------- */
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        /* @validation rules 
         ------------------------------------------ */
        rules: {
            "form:name": {
                required: true
            },
            "form:pattern": {
                required: true
            },
            "form:mattern": {
                required: true
            },
            "form:email": {
                required: true
            },
            "form:charge": {
                required: true
            },
            "form:dependency": {
                required: true
            }
        },
        /* @validation error messages 
         ---------------------------------------------- */

        messages: {
            "form:name": {
                required: "Por favor ingrese un nombre"
            },
            "form:pattern": {
                required: "Por favor ingrese un apellido paterno"
            },
            "form:mattern": {
                required: "Por favor ingrese un apellido materno"
            },
            "form:email": {
                required: "Por favor ingrese un correo"
            },
            "form:charge": {
                required: "Por favor ingrese un cargo"
            },
            "form:dependency": {
                required: "Por favor ingrese una dependencia"
            }
        },
        /* @validation highlighting + error placement  
         ---------------------------------------------------- */

        highlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').addClass(errorClass).removeClass(validClass);
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').removeClass(errorClass).addClass(validClass);
        },
        errorPlacement: function (error, element) {
            element.parent().remove(".has-error");
            error.insertAfter(element);
        }

    });
//    $("#form\\:code").focus();
//    $("#form").validate({
//        /* @validation states + elements 
//         ------------------------------------------- */
//        errorClass: "has-error text-danger",
//        validClass: "has-success",
//        errorElement: "em",
//        /* @validation rules 
//         ------------------------------------------ */
//        rules: {
//            "form:name": {
//                required: true
//            },
//            "form:code": {
//                required: true
//            }
//        },
//        /* @validation error messages 
//         ---------------------------------------------- */
//
//        messages: {
//            "form:name": {
//                required: 'Por favor ingrese un nombre'
//            },
//            "form:code": {
//                required: 'Por favor ingrese un codigo'
//            }
//        },
//        /* @validation highlighting + error placement  
//         ---------------------------------------------------- */
//
//        highlight: function (element, errorClass, validClass) {
//            $(element).closest('.form-group').addClass(errorClass).removeClass(validClass);
//        },
//        unhighlight: function (element, errorClass, validClass) {
//            $(element).closest('.form-group').removeClass(errorClass).addClass(validClass);
//        },
//        errorPlacement: function (error, element) {
//            element.parent().remove(".has-error");
//            error.insertAfter(element);
//        }
//
//    });
})

