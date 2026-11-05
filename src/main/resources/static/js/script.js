
// para fazer com que o javascript "link" juntamente do html
const formulario = document.getElementById("FormLogin");

const mensagem = document.getElementById("mensagemLogin");

formulario.addEventListener("submit", function (evt){

    evt.preventDefault()

    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha:").value

    if (!validarEmail(email)){
        mensagem.textContent = "Por Favor, Insira um E-mail válido"
        mensagem.style.color = "red"
        console.log("Email inválido")
    }else if (senha.length < 6){
        mensagem.textContent = "Senha precisa ter pelo o menos 6 dígitos"
        mensagem.style.color = "red"
        console.log("Senha inválida")
    }else{
        mensagem.textContent = "Dados preenchidos corretamente!!"
        mensagem.style.color = "green"
        console.log("Dados preenchidos corretamentr!")
    }
});

function  validarEmail(email){
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email)
}