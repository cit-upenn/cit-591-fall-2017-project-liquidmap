# LiquidMap

## Summary

LiquidMap is a java application designed to create an artistic animated image of a city's roads.  This is done by defining a up to a thousand trips that are all pictorially represented at once.

## Technique

### Geographic DataSourcs

The tool to generate routes is based on the fabulous project [GraphHopper](https://github.com/graphhopper/graphhopper).  In order to operate, GraphHopper requires a map file such as the included "philadelphia.osm.pbf".  On the first run, this file will generate derived networks that can be used to rapidly fine routes.  The map file is specified with `cityMapFile`.

|  `cityMapFile`  |  "philadelphia.osm.pbf"  |

Trips have a beginning and an end.  In order to efficiently specify the hundreds of trips, we need a way of describing hundreds of locations.  We do this using `DataSource`s.  A `DataSource` can be either a `VectorDataSource`, or a `RasterDataSource`.  Each `DataSource` is given a name so that it can be easily addressed later.  One may specify many `DataSource`s, but ultimately, only two will be used.  There are two variations on `DataSource`.

A `VectorDataSource` is a list of Latitudes and Longitudes from which we draw a random sample.  For instance one could imaging a list of the locations of all fire emergencies in a city for a year or a list consiting of just one point, the locations of your home.

A `RasterDataSource` is a grayscale image of the region where the color denotes some quantity of interest.  For instance, one could use this to denote population density or median household income.  In order to give such an image meaning, one needs to specify two pixels of known location to orient the image on the globe.  It is preferable that these two pixels are diagonal from each other.

### Route Specification
After all of the 'DataSource's have been specified, we must define the family of trips.  This can be done with following parameters
* The name of the starting `DataSource`
* The name of the ending `DataSource`
* The number of trips to be visualized
* The minimum time allowed for a trip
* The maximum time allowed for a trip
* A start time variance (seconds)
* A speed variance.

The process goes as follows:
1. A random location is generated from the starting `DataSource`
1. A random location is generated from the ending `DataSource`
1. A route is found between these two points.
1. If the transit time required to complete that route is outside the minimum and maximum specified time the route is discarded, otherwise it is kept.
1. The trip is velocity is globally altered according to the `speedVariance`.  For instance a value of 0.30 will yield a new trip that will occur randomly between 85% to 115% of the original time.
1. The trip starting point is offset by a random time beween 0 and the timeStartVariance.
1. The trip is added to the list to be visualized.

### Visualization

asdfasdf
## Example JSON File
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
        "imageWidth": 600,
        "pointUpperLeft": {
                "lat": 40.138178,
                "lon": -75.308141
        },
        "pointLowerRight": {
                "lat": 39.85096,
                "lon": -74.929113
        }
    }
}
```

and the ability to control all of the pa

This program creates a "Liquid Map", an animated SVG embedded into a webpage.  It can do this for....