
var ReportView = function () {
    var lastReportElement, lastBg, lastFg;
    return {
        report: function (element, document, bg, fg) {
            if (lastReportElement != undefined) {
                $(lastReportElement).removeClass(lastBg).removeClass("txt-color-white").addClass("btn-default");
                $(lastReportElement).find("i").addClass(lastFg);
            }
            lastReportElement = element;
            lastBg = bg;
            lastFg = fg;
            $(lastReportElement).addClass(lastBg).addClass("txt-color-white").removeClass("btn-default");
            $(lastReportElement).find("i").removeClass(lastFg);
            $("#report-form\\:content-type").val(document);
        },
        download: function () {
            var map = "";
            $(".parameter-report").each(function () {
                if (map.length != 0)
                    map += ";";
                map += $(this).attr("id") + "=" + $(this).val();
            })
            $("#report-form\\:parameters").val(map);
            $("#report-form\\:download-rp").trigger("click");
        }
    };
}();

$(function () {
})

