gulp       = require 'gulp'
coffeeify  = require 'gulp-coffeeify'
coffeelint = require 'gulp-coffeelint'


gulp.task 'build', ->
    gulp.src './simple.datagrid.coffee'
        .pipe coffeeify()
        .pipe gulp.dest('./')

gulp.task 'lint', ->
    gulp.src ['./simple.datagrid.coffee', './simple.widget.coffee']
        .pipe coffeelint()
        .pipe coffeelint.reporter()

gulp.task 'watch', ['build'], ->
    gulp.watch ['./simple.datagrid.coffee', './simple.widget.coffee'], ['default']

gulp.task 'default', ['build']
