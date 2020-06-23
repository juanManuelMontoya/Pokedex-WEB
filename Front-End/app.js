// DOM objects
const pokedex = document.getElementById('pokedex');
const regions = document.getElementById('regions');
const pokeName = document.querySelector('.poke-name');
const pokeId = document.querySelector('.poke-id');
const pokeFrontImage = document.querySelector('.poke-front-image');
const pokeBackImage = document.querySelector('.poke-back-image');
const pokeTypeone = document.querySelector('.poke-type-one');
const pokeTypetwo = document.querySelector('.poke-type-two');
const pokeWeight = document.querySelector('.poke-weight');
const pokeHeight = document.querySelector('.poke-height');
const pokeAbilities = document.querySelector('.poke-abilities');
const modal = document.querySelector('#modal-content');
const regionImage = document.querySelector('.region-image');
const regionLocation = document.querySelector('.region-locations');
const regionName = document.querySelector('.region-name');
const serverOf = document.querySelector('.server-off');


// Constants and Variables
let init= 1;
let limit = 20
let urlRegion = "https://pokespring-app.herokuapp.com/region/"; //"https://pokeapi.co/api/v2/region/";
let urlPokemon = "https://pokespring-app.herokuapp.com/pokemon/"; //"https://pokeapi.co/api/v2/pokemon/";
const TYPES = [
    'normal', 'fighting', 'flying', 'poison', 'ground',
    'rock', 'bug', 'ghost', 'steel', 'fire', 'water',
    'grass', 'electric', 'psychic', 'ice', 'dragon',
    'dark', 'fairy'
];

// Functions

const fetchPokemons = () => {
    const promises = [];
  for (init; init <= limit; init++) {
        const url = urlPokemon + init;
        promises.push(fetch(url).then((res) => res.json()));
    }
    Promise.all(promises).then((results) => {
        const pokemon = results.map((result) => ({
            name: result.name,
            image: result.sprites['front_default'],
            type: result.types.map((type) => type.type.name).join(', '),
            id: result.id
        }));

        displayPokemon(pokemon);
    });
    
};

const displayPokemon = (pokemon) => {
    const pokemonHTMLString = pokemon
        .map(
            (pokeman) => `
        <li onclick="fetchPokemon(${pokeman.id})" class="card" data-toggle="modal" data-target="#exampleModal">
            <img class="card-image" src="${pokeman.image}"/>
            <h2 class="card-title">${pokeman.id}. ${pokeman.name}</h2>
            <p class="card-subtitle">Type: ${pokeman.type}</p>
        </li>
    `
        )
        .join('');
    pokedex.innerHTML = pokemonHTMLString;
};

const fetchPokemon = (id) =>{
    const url = urlPokemon + id;
    fetch(url).then( res => {
        return res.json();
    }).then(data => {

        resetScreen();
        pokeName.textContent = capitalize(data['name']);
        pokeId.textContent = "#"+data['id'].toString().padStart(3, '0');
        pokeWeight.textContent = data['weight'];
        pokeHeight.textContent = data['height'];

        const dataTypes = data['types'];
        const dataTypeOne = dataTypes[0];
        const dataTypeTwo = dataTypes[1];
        pokeTypeone.textContent = capitalize(dataTypeOne['type']['name']);

        if(dataTypeTwo){
            pokeTypetwo.classList.remove('hide');
            pokeTypetwo.textContent = capitalize(dataTypeTwo['type']['name']);
        }else{
            pokeTypetwo.classList.add('hide');
            pokeTypetwo.textContent = "";
        }

        modal.classList.add(dataTypeOne['type']['name']);
        pokeAbilities.textContent =capitalize(data['abilities'][0]['ability']['name']) +
         " - " + capitalize(data['abilities'][1]['ability']['name']);
        pokeFrontImage.src = data['sprites']['front_default'] || "";
        pokeBackImage.src = data['sprites']['back_default'] || "";
        
    })
}

const fetchRegion = (id) => { 
    const url = urlRegion + id;
    fetch(url).then( res => {
        return res.json();
    }).then(data =>{
       regionName.textContent = capitalize(data['name']);
       regionImage.src = "assets/"+data['name']+".jpg";
    });

}

const next = () =>  {
    limit += 20;
    fetchPokemons();
}

const previus = () => {
    if(init != 21){
        init -= 40;
        limit -= 20;
        console.log(init + " " + limit);
        fetchPokemons();
    }
}

const resetScreen = () => {
    for(const type of TYPES){
        modal.classList.remove(type);
    }
}

window.addEventListener("load", function(){
    const loader = document.querySelector('.loader');
    loader.className += ' hidden';
})

const capitalize = (str) => str[0].toUpperCase() + str.substr(1);

fetchPokemons();
