# LiquidMaps

## Summary

LiquidMaps is a Java application designed to create an artistic animated image of a city's roads. This is done by defining up to a thousand trips that are all pictorially represented at once.

## Technique

### Geographic DataSources

The tool to generate routes is based on the fabulous project [GraphHopper](https://github.com/graphhopper/graphhopper). In order to operate, GraphHopper requires a map file such as the included "philadelphia.osm.pbf". On the first run, this file will generate derived networks that can be used to rapidly find routes. The map file is specified with `cityMapFile`.

Trips have a beginning and an end. In order to efficiently specify the hundreds of trips, we need a way of describing hundreds of locations. We do this using `DataSource`s. A `DataSource` can be either a `VectorDataSource` or a `RasterDataSource`. Each `DataSource` is given a name so that it can be easily addressed later. One may specify many `DataSource`s, but ultimately, only two will be used. There are two variations on `DataSource`.

A `VectorDataSource` is a list of latitudes and longitudes from which we draw a random sample. For instance, one could imagine a list of the locations of all fire emergencies in a city for a year or a list consisting of just one point (e.g., the location of your home).

A `RasterDataSource` is a grayscale image of the region where the color denotes some quantity of interest. For instance, one could use this to denote population density or median household income. In order to give such an image meaning, one needs to specify two pixels of known location to orient the image on the globe. It is preferable that these two pixels are diagonal from each other.

### Route Specification
After all of the `DataSource`s have been specified, we must define the family of trips. This can be done with the following parameters:
* The name of the starting `DataSource`
* The name of the ending `DataSource`
* The number of trips to be visualized
* The minimum time allowed for a trip
* The maximum time allowed for a trip
* A start time variance (seconds)
* A speed variance

The process goes as follows:
1. A random location is generated from the starting `DataSource`
2. A random location is generated from the ending `DataSource`
3. A route is found between these two points.
4. If the transit time required to complete that route is outside the minimum and maximum specified time, the route is discarded; otherwise, it is kept.
5. The trip's velocity is globally altered according to the `speedVariance`. For instance, a value of 0.30 will yield a new trip that will occur randomly between 85% to 115% of the original time.
6. The trip's start time is offset by a random time between 0 and the timeStartVariance.
7. The trip is added to the list to be visualized.

### Visualization
In order to animate the list of trips, all trip information must first be converted from world space (i.e., latitude and longitude in degrees) to screen space (i.e., x and y in pixels). This is accomplished using the `Converter`. The classes `PointWorld` and `PointScreen` both inherit from the abstract superclass `Point`, and the `Converter` can turn `PointWorld` objects to `PointScreen` objects. The converted trips have all of the same features and behavior as the original trips, except they are expressed in pixels rather than degrees.

The converted trips are passed to the `Animator`, which generates HTML, CSS, and JS code that draws each trip as a path and animates it according to user-specified settings. The JavaScript class [Segment](https://github.com/lmgonzalves/segment) is used to draw sections of each path using percentages (e.g., draw the segment between 45% and 55%), and durations and delays are calculated appropriately to ensure that each leg of each trip is animated at the correct speed. The code is written to an .html file.

## User Customization

### Settings

The input and output of LiquidMaps can be adjusted by modifying the fields of the settings file (settings.json). A description of each field is provided below.


### Example Settings File

Below is the text of a correctly written settings file (settings.json). This can be used as a model when customizing the LiquidMaps settings.

```
{
	"cityMapFile": "philadelphia.osm.pbf",
	"rasterDataDescs": [
		{
			"name": "pop2012",
			"mapFileName": "PhillyPopDensity2012.png",
			"point1": {
				"lat": 40.138178,
				"lon": -75.308141
			},
			"point2": {
				"lat": 39.85096,
				"lon": -74.929113
			},
			"pixel1": {
				"pixelX": 0,
				"pixelY": 0
			},
			"pixel2": {
				"pixelX": 550,
				"pixelY": 550
			}
		}
	],
	"vectorDataDescs": [
		{
			"name": "home",
			"vecFileName": "VDSTest.txt"
		}
	],
	"routingVars": {
		"routeBeg": "pop2012",
		"routeEnd": "pop2012",
		"routeCount": 200,
		"routeMinTime": 5,
		"routeMaxTime": 10000,
		"timeStartVariance": 100,
		"speedVariance": 0.20
	},
	"outputVars": {
		"pointUpperLeft": {
			"lat": 40.138178,
			"lon": -75.308141
		},
		"pointLowerRight": {
			"lat": 39.85096,
			"lon": -74.929113
		},
		"strFileName": "animation",
		"strPageTitle": "LiquidMaps: Philadelphia",
		"strCanvasText": "",
		"intCanvasWidth": 600,
		"strCanvasColor": "#000000",
		"intLineWidth": 2,
		"intLineLength": 500,
		"isKeepLinesVisible": true,
		"strLineColorA": "#AAFF88",
		"strLineColorB": "#FFAA88",
		"strTextColor": "#FFFFFF",
		"dblTimeBetweenSpawns": 0
    }
}
```


