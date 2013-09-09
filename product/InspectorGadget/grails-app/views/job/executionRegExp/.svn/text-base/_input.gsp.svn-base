<g:textField name="executionRegExp" maxlength="100" id="regexp" value="${jobInstance?.executionRegExp}"/>
<span id="image" class="validation-error"></span>
<a href="#" id="validateLink">Validate</a>

<label><a href="http://quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-06" target="_blank">Regular Expression Tutorial</a></label>

<g:javascript>
$(document).ready(function() {
    $( "#validateLink" ).click(function() {
        var url = "${createLink(controller: "job", action: "validateCronExpression")}?expression=" + $("#regexp").val();
        console.debug(url);

        $.getJSON(url, function(data) {
            var isValid = data.valid;
            var element = $("#image");
            if (isValid) {
                element.removeClass("validation-error");
                element.addClass("validation-ok");
            } else {
                element.removeClass("validation-ok");
                element.addClass("validation-error");
            }
        });
    });
});
</g:javascript>