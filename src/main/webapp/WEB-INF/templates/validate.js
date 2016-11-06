/**
 * @return {boolean}
 */
LoginValid = function (request, response) {
    var login = $("#login").val();
    log = new RegExp('^((\\d|[A-Z]|[a-z]){4,})$');
    var validate = log.test(login);
    if (validate) {
        $.ajax({
            url: "/validate",
            data: {"login": login},
            dataType: "json",
            async: false,
            success: function (response_data) {
                if (response_data.results == true) {
                    $("#err_login").html("Этот логин занят");
                    validate = false;
                }
                else
                    $('#err_login').html("hhh");
            }
        });
    } else {
        if (login.length < 5)
            $("#err_login").html("логин должен состоять не меньше чем из 4 латинских букв или цифр");
        else
            $("#err_login").html("некорректные символы");
    }
    return validate;
};
/**
 * @return {boolean}
 */
PasswordValid = function () {
    var password = $('#password').val();
    log = new RegExp('^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$');
    var validate = log.test(password);
    if (validate == false) {
        $("#err_password").html("Пароль недостаточно сложен: должны быть цифры, заглавные и строчные буквы и длина минимум 8 символов");
    }
    else {
        $('#err_password').html("hhhhhhhhh");
    }
    return validate;
};
/**
 * @return {boolean}
 */
ConfirmPasswordValid = function () {
    var password = $('#password').val();
    var confirm_password = $('#confirm_password').val();
    var validate = (password == confirm_password);
    if (validate == false) {
        $("#err_confirm_password").html("пароли не совпадают");
    }
    else {
        $('#err_confirm_password').html("we54678u");
    }
    return validate;
};