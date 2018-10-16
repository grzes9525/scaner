import 'ol/ol.css';
import {Map, View} from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';

const map = new Map({
  target: 'map',
  layers: [
    new TileLayer({
      source: new OSM()
    })
  ],
  view: new View({
    center: [0, 0],
    zoom: 0
  })
});

var iconFeatures=[];

var iconFeature = new ol.Feature({
  geometry: new ol.geom.Point(ol.proj.transform([-72.0704, 46.678], 'EPSG:4326',     
  'EPSG:3857')),
  name: 'Null Island',
  population: 4000,
  rainfall: 500
});

var iconFeature1 = new ol.Feature({
  geometry: new ol.geom.Point(ol.proj.transform([-73.1234, 45.678], 'EPSG:4326',     
  'EPSG:3857')),
  name: 'Null Island Two',
  population: 4001,
  rainfall: 501
});

iconFeatures.push(iconFeature);
iconFeatures.push(iconFeature1);

var vectorSource = new ol.source.Vector({
  features: iconFeatures //add an array of features
});

var iconStyle = new ol.style.Style({
  image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
    anchor: [0.5, 46],
    anchorXUnits: 'fraction',
    anchorYUnits: 'pixels',
    opacity: 0.75,
    src: 'https://pl.wikipedia.org/wiki/Portable_Network_Graphics#/media/File:PNG_transparency_demonstration_1.png'
  }))
});

var vectorLayer = new ol.layer.Vector({
  source: vectorSource,
  style: iconStyle
});
