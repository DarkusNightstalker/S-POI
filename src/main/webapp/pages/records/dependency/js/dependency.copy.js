/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var DependencyCopy = function () {
    return {
        init: function () {
        },
        begin: function () {
            beginDependencyCopy();
        },
        endBegin: function () {
            $("#form-dependency-copy").validate({
                errorClass: "has-error text-danger",
                validClass: "has-success",
                errorElement: "em",

                highlight: function (element, errorClass, validClass) {
                    $(element).closest('td').addClass(errorClass).removeClass(validClass);
                },
                unhighlight: function (element, errorClass, validClass) {
                    $(element).closest('td').removeClass(errorClass).addClass(validClass);
                },
                errorPlacement: function (error, element) {
                    error.insertAfter(element);
                }
            });
            $('#modal-dependency-copy').modal({backdrop: 'static', keyboard: false})
        },
        beginSave: function () {
            if ($("#form-dependency-copy").valid()) {
                saveDependencyCopy();
            } else {
                $.smallBox({
                    title: "Formulario Invalido",
                    content: "Algunos campos no son validos",
                    color: "#C46A69",
                    timeout: 5000,
                    icon: "fa fa-warning animated shaked"
                });
            }
        },
        remove: function (index) {
            $("#form-dependency-copy\\:data tr[data-index=" + index + "]").remove();
        }
    };
}();
$(function () {
    DependencyCopy.init();
});
