
const formulario = document.getElementById("formVeiculo");

formulario.addEventListener("submit", async function(evt) {

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
        estadoUF: document.getElementById("estadoUF").value
    };

    const resposta = await fetch("http://localhost:8080/api/VeiculoModel", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(Veiculo)
    });

    if (resposta.ok) {
        document.getElementById("mensagem").textContent = "Veículo cadastrado com sucesso!";
        formulario.reset();
    } else {
        document.getElementById("mensagem").textContent = "Erro ao cadastrar veículo.";
    }

});

async function listarVeiculos (){
    const resposta = await fetch("http://localhost:8080/api/VeiculoModel");
    if (resposta.ok) {

        const veiculos = await resposta.json();

        const lista = document.getElementById("listaVeiculos");
        lista.innerHTML = "";

        lista.forEach(function (Veiculo) {

            const item = document.createElement("div")

            item.innerHTML = `
            <h3>${veiculo.marca} ${veiculo.modelo}</h3>
            <p>Ano: ${veiculo.anoFabricacao}/${veiculo.anoModelo}</p>
            <p>Versão: ${veiculo.versao}</p>
            <p>Motorização: ${veiculo.motorizacao}</p>
            <p>Combustível: ${veiculo.combustivel}</p>
            <p>Quilometragem: ${veiculo.quilometragem} km</p>
            <p>Estado: ${veiculo.estadoUF}</p>
            <hr>
        `;
            lista.appendChild(item);
        });
    }else{
        console.log("Error ao carregar veículo")
    }
    console.log(veiculos)
}
listarVeiculos();