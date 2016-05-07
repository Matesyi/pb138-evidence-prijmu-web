$(function() {
    $.mockjax({
        url: '*',
        responseText: ExampleData.fruits.slice(0, 5),
        responseTime: 0
    });

    $('#demo-table').simple_datagrid();
});
