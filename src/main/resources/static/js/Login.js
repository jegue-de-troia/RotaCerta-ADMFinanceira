const formulario = document.getElementById("FormLogin");
const mensagem = document.getElementById("mensagemLogin");

formulario.addEventListener("submit", async function (evt){

    evt.preventDefault()

    const email = document.getElementById("email").value.trim();
    if(!validarEmail(email)){
        mensagem.textContent = "Por Favor, insira um Email válido"
        mensagem.style.color = "red";
        return
    }

    const resposta = await fetch("/api/usuario",{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({email: email})
    });

    if(resposta.ok){
        const usuario = await resposta.json();
        localStorage.setItem("usuarioId", usuario.id)
        localStorage.setItem("usuarioEmail", usuario.email);
        window.location.href = "Index.html";
    }else{
        mensagem.textContent = "Error ao entrar. Tente Novamente";
        mensagem.style.color = "red";
    }
});

function  validarEmail(email){
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email)
}

