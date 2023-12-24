var map = L.map('map').setView([41.994626, 21.430379], 13);
const attribution = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';
const tileUrl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
const tiles = L.tileLayer(tileUrl, {attribution})
tiles.addTo(map);

map.on('click', function (e) {
    var coord = e.latlng;
    var lat = coord.lat;
    var lng = coord.lng;

    // Fill the input fields with the latitude and longitude values
    document.getElementById('xC').value = lng;
    document.getElementById('yC').value = lat;
    document.getElementById("map").style.width = (screen.width - 300).toString();
    document.getElementById("map").style.margin = "0px 0px 0px 320px"
    open = true;
    document.getElementById("sidebar").hidden = false;
    document.getElementById("catButtons").style.left = "380px"
    console.log("You clicked the map at latitude: " + lat + " and longitude: " + lng);
});
let open = false;


function menuClick() {
    if (open) {
        document.getElementById("map").style.width = screen.width.toString();
        document.getElementById("map").style.margin = "0px"
        open = false;
        document.getElementById("sidebar").hidden = true;
        document.getElementById("catButtons").style.left = "60px";
    } else {
        document.getElementById("map").style.width = (screen.width - 300).toString();
        document.getElementById("map").style.margin = "0px 0px 0px 320px"
        open = true;
        document.getElementById("sidebar").hidden = false;
        document.getElementById("catButtons").style.left = "380px"
    }
}

var markers = new Array();


async function loadAllLocations() {
    let items = document.getElementById("locs").value;
    let parsed = JSON.parse(items);

    for (let item of parsed) {
        let marker = L.marker([item.y, item.x]).bindPopup("<h3>" + item.name + "</h3>");
        markers.push(marker);
        map.addLayer(marker);
    }
}

function openRegister() {
    document.getElementById("registerBtn").click();
}


function initMap() {
    map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM(),
            }),
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([0, 0]),
            zoom: 2,
        }),
    });
}

loadAllLocations();

