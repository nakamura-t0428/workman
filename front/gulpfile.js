var gulp = require('gulp');

var tsConfig = require('./tsconfig.json');

var bower = require('gulp-bower');
var tsd = require('gulp-tsd');
var ts = require('gulp-typescript');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var webpack = require('gulp-webpack');
var sourcemaps = require('gulp-sourcemaps');
var htmlmin = require('gulp-htmlmin');
var imagemin = require('gulp-imagemin');
var copy = require('gulp-copy');
var autoprefixer = require('gulp-autoprefixer');
require('es6-promise').polyfill(); // for autoprefixer
var cssmin = require('gulp-cssmin');
var path = require("path");
var del = require('del');
var connect = require('gulp-connect');
var runSequence = require('run-sequence');
var webpackStream = require('webpack-stream');
var webpack = require('webpack');
var BowerWebpackPlugin = require("bower-webpack-plugin");
var sass = require('gulp-sass');
var templateCache = require('gulp-angular-templatecache');
var flatten = require('gulp-flatten');

var bowerWebpackPlugin = new BowerWebpackPlugin({
  modulesDirectories: ["bower_components"],
  manifestFiles:      [".bower.json","bower.json"],
  includes:           /.*/,
  excludes:           [],
  searchResolveModulesDirectories: false
});

var uglifyJsPlugin = new webpack.optimize.UglifyJsPlugin();
var tsWebpackModule = {
  loaders: [
    {
      test: /\.ts$/,
      loader: "awesome-typescript-loader"
    },
    {
      test: /\.css$/,
      loaders: ['style', 'css']
    },
  ]
}
var tsWebpackOutput = {
  filename: 'js/[name]-app.js',
  devtoolModuleFilenameTemplate: '../../[resource-path]',
  devtoolFallbackModuleFilenameTemplate: '../../[resource-path]?[hash]',
}

gulp.task('bower', function() {
  return bower({
    command: 'install'
  });
});

gulp.task('tsd', function (callback) {
  tsd({
      command: 'reinstall',
      latest: true,
      config: './tsd.json'
  }, callback);
});

// TypeScript Task
gulp.task('ts_main', ['tsd', 'bower'], function () {
  return gulp.src(['./src/ts/**/*'])
    .pipe(webpackStream({
      displayErrorDetails: true,
      devtool: '#source-map',
      resolve: {
        extensions: ['', '.ts', '.webpack.js', '.web.js', '.js', '.css']
       },
      entry: {main: './src/ts/app.ts'},
      output: tsWebpackOutput,
      plugins: [bowerWebpackPlugin,
        // uglifyJsPlugin
      ],
      module: tsWebpackModule,
    }))
    // .pipe(sourcemaps())
    .pipe(gulp.dest('dist/'));
});

gulp.task('ts', ['ts_main']);

gulp.task('main-template', function(){
  return gulp.src(['./src/views/**/*.html'])
    .pipe(templateCache('main-template.js', {
      root: '',
      module: 'main.app',
      standalone: false,
    }))
    .pipe(gulp.dest('./dist/js'));
});

gulp.task('html', function(){
  return gulp.src(['./src/html/**/*.html'], {base:'./src/html'})
    .pipe(htmlmin({
      removeComments: true,
      removeCommentsFromCDATA: true,
      removeCDATASectionsFromCDATA: true,
      collapseWhitespace: true,
      removeRedundantAttributes: true,
      removeOptionalTags: true
    }))
    .pipe(gulp.dest('./dist'));
});

gulp.task('image', function(){
  var result = gulp.src(['./src/**/*.{png,jpg,gif}'])
  .pipe(imagemin())
  .pipe(gulp.dest('./dist'));
  return result;
});

gulp.task('js', function(){
  return gulp.src(
    [
      './bower_components/jquery/dist/jquery.min.js',
      './bower_components/bootstrap-sass/assets/javascripts/bootstrap.min.js',
      './bower_components/angular/angular.min.js',
      './bower_components/angular-resource/angular-resource.min.js',
      './bower_components/angular-animate/angular-animate.min.js',
      './bower_components/angular-bootstrap/ui-bootstrap.min.js',
      './bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js',
      './bower_components/angular-ui-router/release/angular-ui-router.min.js',
      './bower_components/angular-loading-bar/build/loading-bar.min.js',
      './bower_components/angular-ui-grid/ui-grid.min.js',
      './bower_components/ngstorage/ngStorage.min.js',
      ])
    .pipe(concat('common-components.js'))
    .pipe(gulp.dest('./dist/js'));
});

gulp.task('css', function(){
  return gulp.src('./src/**/*.scss')
    .pipe(sass())
    .on('error', function(err) {
      console.log(err.message);
    })
    .pipe(autoprefixer({
      browsers: ['last 2 version', 'ie 8', 'ie 9']
    }))
    .pipe(cssmin())
    .pipe(gulp.dest('./dist'));
});

gulp.task('font', ['bower'], function(){
  return gulp.src(
    [
      './bower_components/bootstrap-sass/assets/fonts/bootstrap/*.{eot,woff2,woff,ttf,svg}',
      './bower_components/angular-ui-grid/*.{eot,woff2,woff,ttf,svg}',
      ])
    .pipe(flatten())
    .pipe(gulp.dest('./dist/fonts'));
});

gulp.task('clean', function(cb){
  del(['./dist'], cb);
});

gulp.task('connect', function(){
  connect.server({
    root: './dist',
    livereload: true
  });
});

// Watch
gulp.task('watch', function () {
    gulp.watch(['src/ts/**/*'], ['ts']);
    gulp.watch(['src/html/**/*.html'], ['html']);
    gulp.watch(['src/views/**/*.html'], ['main-template']);
    gulp.watch(['src/**/*.{png,jpg,gif}'], ['image']);
    gulp.watch(['src/**/*.scss'], ['css']);
});

gulp.task('dist', ['main-template', 'js', 'ts', 'css', 'image', 'html', 'font']);
gulp.task('clean-for-release', function(cb){
  del(['./dist/**/*.map'], cb);
});
gulp.task('release', function(cb) {
  runSequence('dist', 'clean-for-release', cb);
});

// Default Task
gulp.task('default', function(cb){
  runSequence('dist', ['connect', 'watch'], cb);
});
