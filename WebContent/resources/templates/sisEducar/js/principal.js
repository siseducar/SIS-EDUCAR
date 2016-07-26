function validarEmail(email, validacao) {
    var ck_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    var valid = document.getElementById(validacao);
    result = ck_email.test(email);
    if (!result) {
        valid.innerHTML = "Endereço de e-mail inválido";
    } else {
        valid.innerHTML = "Endereço de e-mail válido";
    }
    return result;
}