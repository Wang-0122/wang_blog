function prePage() {
    var currPage = $(".is-active").text();
    $.ajax({
        type: 'POST',//方法类型
        url: "/findPage",
        data:{pageNum:(parseInt(currPage) - 1)},
        // data: {pageNum:parseInt(currPage) - 1,pageSize:9},
        success: function (result) {
            // swal({
            //     title: result.message,
            //     type: 'success',
            //     showCancelButton: false,
            //     confirmButtonColor: '#3085d6',
            //     confirmButtonText: '返回博客列表',
            //     confirmButtonClass: 'btn btn-success',
            //     buttonsStyling: false
            // }).then(function () {
            writePage(result);
            // })


        },
        error: function () {
            alert("操作失败");
        }
    });
}

function selectPage(th){
    var page = $(th).text();
    $.ajax({
        type: 'POST',//方法类型
        url: "/findPage",
        data: {pageNum:page},
        success: function (result) {
            // swal({
            //     title: result.message,
            //     type: 'success',
            //     showCancelButton: false,
            //     confirmButtonColor: '#3085d6',
            //     confirmButtonText: '返回博客列表',
            //     confirmButtonClass: 'btn btn-success',
            //     buttonsStyling: false
            // }).then(function () {
            writePage(result);

            // })


        },
        error: function () {
            alert("操作失败");
        },
    });


}

function nextPage() {
    var currPage = $(".is-active").text();
    $.ajax({
        type: 'POST',//方法类型
        url: "/findPage",
        data: {pageNum:parseInt(currPage) + 1},
            success: function (result) {
                // swal({
                //     title: result.message,
                //     type: 'success',
                //     showCancelButton: false,
                //     confirmButtonColor: '#3085d6',
                //     confirmButtonText: '返回博客列表',
                //     confirmButtonClass: 'btn btn-success',
                //     buttonsStyling: false
                // }).then(function () {
                writePage(result);

                // })


            },
            error: function () {
                alert("操作失败");
            },
        });
};


function writePage(result) {
    if (result.data){
        $("#out-content").empty();
        console.log(result)
        var tmp = '';
        if (result.resultCode != 404){
            if (result.data.list.length != 0){
                for(var i = 0; i < result.data.list.length; i++){
                    tmp += '<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4 col-xl-4">';
                    tmp += '<a id="'+result.data.list[i].demoNum+'" data-id="'+result.data.list[i].blogId+'" href="#modal_animate">';
                    tmp += '<img src="'+result.data.list[i].blogCoverImage+'" alt="Image" class="img-fluid tm-img"> </a> </div>';
                }
            }
        }else{
            tmp = '<h1>"+result.message+"</h1>'
        }

        console.log(tmp);
        $("#out-content").append(tmp);
        init();


        var page = '<a id="page-prev" onclick="prePage()" href="#"><i></i>上一页</a>\n';
        for(var i = 0; i < result.data.totalPage; i++ ){
            // 頁碼大于7 格式如下
            // 123 ... totalPage-3,totalPage-2,totalPage-1
            if (result.data.totalPage > 7){
                if (i < 2){
                    if (i == 0 && result.data.currPage != 1){
                        page +=' <a class="" onclick="selectPage(this)" herf="/page/'+(result.data.currPage-1)+'">'+(result.data.currPage-1)+'</a>\n';
                    }
                    if(i == 1){
                        page +=' <a class="" onclick="selectPage(this)" herf="/page/'+(result.data.currPage)+'">'+(result.data.currPage)+'</a>\n';
                    }

                }
                if (i == 3){
                    var curr = $(".is-active").text();
                    if (parseInt(curr)+1 != result.data.totalPage-1 && parseInt(curr)+1 != result.data.totalPage) {
                        page += '<a class="" herf="#">...</a>\\n';
                        page +=' <a onclick="selectPage(this)" class="" herf="/page/'+(result.data.totalPage-1)+'">'+(result.data.totalPage-1)+'</a>\n';
                    }
                    if (parseInt(curr)+1 != result.data.totalPage) {
                        page +=' <a onclick="selectPage(this)" class="" herf="/page/'+(result.data.totalPage)+'">'+(result.data.totalPage)+'</a>\n';
                    }
                }


            } else{
                page +=' <a class="" herf="#">'+(i+1)+'</a>\n';
            }
        }
        $("#page-normal").empty();
        page += '<a id="page-next" onclick="nextPage()" href="#">下一页<i></i></a>';
        $("#page-normal").append(page);
        if (result.data.currPage == 1){
            $("#page-prev").css("pointer-events","none");
        }
        if (result.data.currPage == result.data.totalPage || result.data.currPage + 1 == result.data.totalPage-1 ){
            $("#page-next").css("pointer-events","none");
        }


        var arr = $("#page-normal").find('a');
        if(arr && arr.length != 0){
            $(arr).each(function (idx,val) {
                var page = $(arr[idx]).text();
                console.log(page + "---當前頁" + result.data.currPage );
                if (result.data.currPage == page){
                    $(arr[idx]).addClass('is-active');
                }
            });
        }

    }
}



function init(){
    $("#demo01").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        beforeOpen: function () {     //弹出前的回调
            console.log("The animation was called");
        },
        afterOpen: function () {    //弹出后
            var id = $("#demo01").data("id");
            findById(id);
        },
        beforeClose: function () {  //关闭前
            console.log("The animation was called");
        },
        afterClose: function () {    //关闭后
            console.log("The animation is completed");
        }
    });

    $("#demo02").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo02").data("id");
            findById(id);
        }
    });

    $("#demo03").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo03").data("id");
            findById(id);
        }
    });

    $("#demo04").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo04").data("id");
            findById(id);
        }
    });

    $("#demo05").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo05").data("id");
            findById(id);
        }
    });

    $("#demo06").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo06").data("id");
            findById(id);
        }
    });

    $("#demo07").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo07").data("id");
            findById(id);
        }
    });

    $("#demo08").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo08").data("id");
            findById(id);
        }
    });

    $("#demo09").animatedModal({    // 默认点击#demo02触发
        modalTarget: 'modal_animate',      // 弹窗的ID名
        // animatedIn: 'lightSpeedIn',   //弹出淡入效果
        // animatedOut: 'bounceOutDown', //关闭时的淡出效果
        // color: 'green',                 //背景颜色
        // animationDuration: '2s',     //过渡时间
        // overflow: 'scroll',         //是否要滚动条
        // Callbacks
        afterOpen: function () {    //弹出后
            var id = $("#demo09").data("id");
            findById(id);
        }
    });
}