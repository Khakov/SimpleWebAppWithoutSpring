<#ftl encoding='UTF-8'>
<head>
    <meta charset="UTF-8">
</head>
<#if error??>
<h2 style="text-align: center; color: red">${error}</h2>
</#if>
<#if success??>
<h2 style="text-align: center; color: green">${success}</h2>
</#if>
<div>
    <section class="form-group">
        <form id="loginForm" action="/sign-in" method="post">
            <h1>Login Form</h1>
            <div>
                <input type="text" placeholder="Username" name="login"/>
            </div>
            <div>
                <input type="password" placeholder="Password" name="password"/>
            </div>
            <input class="log-text" type='checkbox' name='remember_me'> <a class="log-text">Запомнить меня</a><br>
            <div>
                <input type="submit" value="Log in"/>
            </div>
            <div>
                <br/>
                <p>
                    <a href="/sign-up" class="to_register"><i class="fa fa-registered"></i>Зарегестрироваться</a>
                </p>
            </div>
        </form>
    </section>
</div>