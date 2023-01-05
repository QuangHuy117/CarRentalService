
$(function () {
    $("#dropCate").hide();
    $("#labelCate").hide();
    $("#dropSearch").change(function () {
        if ($(this).val() === "Name") {
//            $("#labelName").html("Car Name: ");
            $("#labelName").show();
            $("#textName").show();
            $("#dropCate").hide();
            $("#labelCate").hide();
        } else if ($(this).val() === "Category") {
//            $("#labelCate").html("Car Category: ");
            $("#labelCate").show();
            $("#dropCate").show();
            $("#textName").hide();
            $("#labelName").hide();
        }
    });
    
});


