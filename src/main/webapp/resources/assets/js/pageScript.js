$(document).ready(function(){
    $("#updateBtn").click(function(){
        $(".changeable").removeAttr("disabled");
    });
});

$(document).ready(function(){
    $("#cancelBtn").click(function(){
        $(".changeable").attr("disabled", "disabled");
    });
});

$(document).ready(function(){
    $("#addRoleBtn").click(function(){
        $("#roleForm").attr("class", "form-inline");
    });
});

$(document).ready(function() {

	$("#perPageCategory").change(function() {
		var number = $("#perPageCategory").val();
		var lastCategoryStatus = $("#lastCategoryStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/category/all/" + number + "/" + lastCategoryStatus + "?i=";
	});
});

$(document).ready(function() {

	$("#perPageCountry").change(function() {
		var number = $("#perPageCountry").val();
		var lastCountryStatus = $("#lastCountryStatus").val();
		var context = $("#context").val();
		window.location = context+"/page/country/all/" + number + "/" + lastCountryStatus+"?i=";
	});
});

$(document).ready(function() {

	$("#perPageLanguage").change(function() {
		var number = $("#perPageLanguage").val();
		var lastLanguageStatus = $("#lastLanguageStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/language/all/" + number + "/" + lastLanguageStatus + "?i=";
	});
});

$(document).ready(function() {

	$("#perPageChannel").change(function() {
		var number = $("#perPageChannel").val();
		var lastChannelStatus = $("#lastChannelStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/channel/all/" + number + "/" + lastChannelStatus + "?i=";
	});
});

$(document).ready(function() {

	$("#perPageChannelOwn").change(function() {
		var number = $("#perPageChannelOwn").val();
		var lastChannelStatus = $("#lastChannelStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/ownchannel/all/" + number + "/" + lastChannelStatus + "?i=";
	});
});


$(document).ready(function() {

	$("#perPageVod").change(function() {
		var number = $("#perPageVod").val();
		var lastVodStatus = $("#lastVodStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/vod/all/" + number + "/" + lastVodStatus + "?i=";
	});
});

$(document).ready(function() {

	$("#perPageVodOwn").change(function() {
		var number = $("#perPageVodOwn").val();
		var lastVodStatus = $("#lastVodStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/ownvod/all/" + number + "/" + lastVodStatus + "?i=";
	});
});


$(document).ready(function() {

	$("#perPageTag").change(function() {
		var number = $("#perPageTag").val();
		var lastTagStatus = $("#lastTagStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/tag/all/" + number + "/" + lastTagStatus + "?i=";
	});
});

$(document).ready(function() {

	$("#perPageUser").change(function() {
		var number = $("#perPageUser").val();
		var lastUserStatus = $("#lastUserStatus").val();
		var context = $("#context").val();
		window.location = context + "/page/user/all/" + number + "/" + lastUserStatus + "?i=";
	});
});

$(document).ready(function() {
   var text = $("#serverType").val();
   if(text == "Transcoder"){
	   $(".changeable").hide();
   }
   $("#serverType").change(function() {
	   var text = $("#serverType").val();
	   if(text == "Transcoder"){
		   $(".changeable").hide();
	   }else if(text == "Streamer"){
		   $(".changeable").show();
	   }
	});
});


$(document).ready(function() {

	$(".pageLinkLiveChannel").click(function() {
		var url = $("#context").val() + "/page/searchChannelByTag/result/"+$(this).text();
		//alert(url);
		$.ajax({
		    type : "GET",
		    url : url,
		    success: function(data){
		    $("#channelFragment").html(data);
		    }
		});
	});
});


$(document).ready(function() {

	$(".pageLinkVod").click(function() {
		var url = $("#context").val() + "/page/searchVodByTag/result/"+$(this).text();
		//alert(url);
		$.ajax({
		    type : "GET",
		    url : url,
		    success: function(data){
		    $("#vodFragment").html(data);
		    }
		});
	});
});

$(document).ready(function() {
	var $checkboxes = $("input:checkbox");
    $checkboxes.click(function(){
    	
    	var category = $('input[name="category"]:checked').map(function() {
            return ""+$(this).val()+"";
        }).get().join(",");
    	
    	var country = $('input[name="country"]:checked').map(function() {
            return ""+$(this).val()+"";
        }).get().join(",");
    	
    	var language = $('input[name="language"]:checked').map(function() {
            return ""+$(this).val()+"";
        }).get().join(",");
    	
    	var contextPath = $("#context").val() + "/page/searchFilter/result?category="+category+"&country="+country+"&language="+language;
    	$.ajax({
    	    type : "GET",
    	    url : contextPath,
    	    success: function(data){
    	    	$("#mainFragment").html(data);
    	    }
    	});
    	
    });  

});

$(document).ready(function() {

	$(".pageLinkLiveChannelFilter").click(function() {
		var url = $("#context").val() + "/page/searchChannelFilter/result/"+$(this).text();
		//alert(url);
		$.ajax({
		    type : "GET",
		    url : url,
		    success: function(data){
		    $("#channelFragment").html(data);
		    }
		});
	});
});


$(document).ready(function() {

	$(".pageLinkVodFilter").click(function() {
		var url = $("#context").val() + "/page/searchVodFilter/result/"+$(this).text();
		//alert(url);
		$.ajax({
		    type : "GET",
		    url : url,
		    success: function(data){
		    $("#vodFragment").html(data);
		    }
		});
	});
});






