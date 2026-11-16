const API = "api/VeiculoModel"

const usuarioId = localStorage.getItem("usuarioId");
if(!usuarioId){
    window.location.href = "Login.html";
}

const formulario = document.getElementById("formVeiculo");
const lista = document.getElementById("listaVeiculos");

if(formulario) {
    formulario.addEventListener("submit", async function (evt) {

        evt.preventDefault();

        const Veiculo = {
            marca: document.getElementById("marca").value,
            modelo: document.getElementById("modelo").value,
            anoFabricacao: Number(document.getElementById("anoFabricacao").value),
            anoModelo: Number(document.getElementById("anoModelo").value),
            versao: document.getElementById("versao").value,
            motorizacao: document.getElementById("motorizacao").value,
            combustivel: document.getElementById("combustivel").value,
            quilometragem: Number(document.getElementById("quilometragem").value),
            estadoUF: document.getElementById("estadoUF").value.toUpperCase(),
            usuarioId: Number(usuarioId)

        };
        const resposta = await fetch(API,{
            method: "POST",
            headers: {"Content-Type": "application/json"    },
            body: JSON.stringify(Veiculo)
        });

        const mensagem = document.getElementById("mensagem")
        if (resposta.ok) {
            mensagem.textContent = "Veículo cadastrado com sucesso!"
            formulario.reset();
            listarVeiculos();
        } else {
            mensagem.textContent = "Error ao cadastrar veículo"
        }
    });
}

async function listarVeiculos(){
    if(!lista){
        return
    }
    const resposta = await fetch(`${API}?usuarioId=${usuarioId}`);
    if (!resposta.ok) {
        console.log("Error ao carregar lista");
        return;
    }
        const veiculos = await resposta.json();
        lista.innerHTML = "";

        veiculos.forEach(function (v) {

            const item = document.createElement("div")
            item.innerHTML = `
            <h3>${v.marca} ${v.modelo}</h3>
            <p>Ano: ${v.anoFabricacao}/${v.anoModelo}</p>
            <p>Versão: ${v.versao}</p>
            <p>Motorização: ${v.motorizacao}</p>
            <p>Combustível: ${v.combustivel}</p>
            <p>Quilometragem: ${v.quilometragem} km</p>
            <p>Estado: ${v.estadoUF}</p>
            <button onclick="deletarVeiculo(${v.id})">Excluir</button>
            <hr>
        `;
            lista.appendChild(item);
        });
}

async function alterarVeiculo(id, veiculo) {

    const resposta = await fetch(`${API}/${id}` , {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(veiculo)
    });
    if (resposta.ok){
        console.log("Veículo cadastrado com sucesso!!");
    }else{
        console.log("Error ao cadastrar Veículo... Tente novamente!!");
    }
}

async function deletarVeiculo(id){
   if (!confirm("Excluir este veículo?")) return;

   const resposta = await fetch(`${API}/${id}`,
       {method: "DELETE"})
    if (resposta.ok){
        listarVeiculos();
    }else{
        console.log("Error ao excluir o veículo... Tente Novamente!!")
    }
}

const botaoAtualizar = document.getElementById("atualizarButton")
if(botaoAtualizar){
    botaoAtualizar.addEventListener("click", listarVeiculos)
}
const botaoSair = document.getElementById("sairbutton");
    if(botaoSair){
        botaoSair.addEventListener("click", function (){
            localStorage.clear();
            window.location.href = "Login.js";
        });
    }



listarVeiculos()