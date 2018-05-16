/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var UserOpt = function () {
    return {
        logout: function (username, e) {
            $.SmartMessageBox({
                title: "<i class='fa fa-sign-out txt-color-orangeDark'></i> ¿Deseas salir <span class='txt-color-orangeDark'><strong>"+username+"</strong></span> ?",
                content: "Esta accion terminara cualquier operacion que estes realizando",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    $("#form-user-opt\\:logout").trigger("click");
                }
            });
            e.preventDefault();
        },
        changeOperationYear : function(new_year){
            $("#form-user-opt\\:operation-year").val(new_year);
            $("#form-user-opt\\:change-year").trigger("click");
        }
    };
}();
var Menu = function () {
    var currentMenu;
    var c;
    return {
        change: function (element, children) {
            if (currentMenu) {
                $(currentMenu).closest("li").removeClass("active");
                if (c) {
                    $(currentMenu).closest(".nav-parent").removeClass("open").removeClass("active");
                    $(currentMenu).closest("ul").css("display", "none");
                } else {
                    $(currentMenu).closest("li").removeClass("active");
                }
            }
            $(element).closest("li").addClass("active");
            if (children) {
                $(element).closest(".nav-parent").addClass("open");
                $(element).closest("ul").css("display", "block");
            } else {
                $(element).closest("li").addClass("active");
            }
            currentMenu = element;
            c = children;
        }
    };
}();
$(function(){
    $("ul.dropdown-menu li a.option-year").on("click",function(){
        UserOpt.changeOperationYear($(this).attr("data-session-year"));
    });
})

