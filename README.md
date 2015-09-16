# Project 2 - Google Image Search

Google Image Search is an android app that allows a user to search for images on web using simple filters. The app utilizes [Google Image Search API](https://developers.google.com/image-search/). Please note that API has been officially deprecated as of May 26, 2011.

Time spent: 25 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for images** by specifying a query and launching a search. Search displays a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
* [x] User can configure advanced search filters such as:
  * [x] Size (small, medium, large, extra-large)
  * [x] Color filter (black, blue, brown, gray, green, etc...)
  * [x] Type (faces, photo, clip art, line art)
  * [x] Site (espn.com)
* [x] Subsequent searches have any filters applied to the search results
* [x] User can tap on any image in results to see the image **full-screen**
* [x] User can **scroll down to see more images**. The maximum number of images is 64 (limited by API).

The following **optional** features are implemented:

* [x] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [x] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [x] User can **share an image** to their friends or email it to themselves
* [x] Replaced Filter Settings Activity with a lightweight modal overlay
* [x] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [x] Used the [StaggeredGridView](https://github.com/f-barth/AndroidStaggeredGrid) to display improve the grid of image results
* [x] User can [zoom or pan images](https://github.com/MikeOrtiz/TouchImageView) displayed in full-screen detail view

The following **additional** features are implemented:

* [x] Using Retrofit
* [x] Using ViewHolder pattern to have more efficient image views on scroll

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![Walkthrough](https://s3.amazonaws.com/uploads.hipchat.com/14477/52355/pHhghDhDGNKW0kL/googlesearch3.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

- TouchImageView takes control of the entire layout. You cannot reallyl have anything relative to it.
- For Picasso to work well with the StaggeredGridView, you must initially specify all height and width yourself. This means to calculate the ratio and then back calculate your own width and heights.
- List view with a lot of photos was laggy. I needed to do a bit of investment with in-memory caching of objects.
- EditText underline color was difficult to change. You cannot just specify a new style per StackOverview. The solution is to use getActivity().getLayoutInflater() instead of the inflater passed in in onCreateView.
- Retrofit was giving me some issues due to lack of documentation.

## Open-source libraries used

- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [TouchImageView](https://github.com/MikeOrtiz/TouchImageView) - Enables zoom and pan on images
- [Retrofit](http://square.github.io/retrofit/) - Networking
- [StaggeredGridView](https://github.com/etsy/AndroidStaggeredGrid) - Etsy like staggered images
