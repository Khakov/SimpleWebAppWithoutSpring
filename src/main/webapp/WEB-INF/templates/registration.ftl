<#ftl encoding='UTF-8'>
<head>
    <meta charset="UTF-8">
    <script src="jquery-2.1.4.js"></script>
    <script src="validate.js"></script>
</head>
<div>
    <section class="login_content">
        <form action="/sign-up" method="post">
            <h1>Create Account</h1>
        <#if error??>
            <h2 style="text-align: center; color: red">${error}</h2>
        </#if>
            <div class="error" id="err_login"></div>
            <div>
                <input type="text" id="login" name="login" placeholder="login" id="login" onblur=" LoginValid()"/>
            </div>
            <div class="error" id="err_password"></div>
            <div>
                <input type="password" id = "password" name="password" placeholder="Password" id="password" onblur="PasswordValid()" required/>
            </div>
            <div class="error" id="err_confirm_password"></div>
            <div>
                <input type="password" id="confirm_password" name="confirm_password" placeholder="confirm password"
                       id="confirm_password" onblur="ConfirmPasswordValid()" required/>
            </div>
            <div>
                <input type="submit" value="Регистрация">
            </div>
        </form>
    </section>
</div>