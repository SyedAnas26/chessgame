
var baseTime = null;
    var skillLevel = null;
    var extratime = null;
    var playerColor = null;
    var mode;
    var challengeType;
    var extraTime=null;
    var difficulty=null;


    var modal=document.getElementById("myModal");
    var diffModal=document.getElementById("difficultyModal");
    var joinModal=document.getElementById("joinModal");
    var pgnAnalyse=document.getElementById("pgnAnalyse");
    var close=document.getElementsByClassName("close")[0];
    var close1=document.getElementsByClassName("close")[1];
    var close2=document.getElementsByClassName("close")[2];

    if(localStorage.getItem("invite")==="true") {
            localStorage.setItem("invite","false");
            location = localStorage.getItem("inviteLink");
        }
        var userId= '';
        var userName= '';
        var uniqueId;
        var path=window.location.href;
        var paths=path.split('/');
        var guest=true;
        var x=window.location.origin;
        var splitPath=path.split('/');
        window.onload = function() {

            $.ajax({
                url: x+"/getUserId",
                type: 'POST',
                success: function(response) {
                    userId=response.user_id;
                    userName=response.user_name;
                    uniqueId=response.unique_id;
                    console.log('userId '+userId);
                    if (userId==='null'){
                        uniqueId='000';
                        guest=true;
                        userName="to LichessPro ";
                        document.getElementById('signin').style.display='block';
                        document.getElementsByClassName('lock')[0].style.display='block';
                        document.getElementsByClassName('primary-blue-button')[0].style.paddingLeft="50px";
                        document.getElementById('signup').style.display='block';
                    }
                    else if(userId!=='null'){
                        if(paths.length<=4){
                            location='/homepage/'+uniqueId;
                        }
                        document.getElementById('logout').style.display='block';
                        document.getElementById('analyseMyHistory').style.display='block';
                        guest=false;
                    }
                }
            });
        }



    function showConfiguration(value){

        if(value=='vsComp' || value=='multi'){
            if(value=='multi'){
                if(guest){
                    alert('LOCKED : Log in to unlock this');
                    return;
                }
            }
            modal.style.display="block";
            diffModal.style.display="block";
            mode=value;
            if (value === 'multi') {
                document.getElementsByClassName("skillLevel")[0].style.display = "none";
                document.getElementsByClassName("skillLevel")[1].style.display = "none";

            }else if(value==='vsComp'){
                document.getElementsByClassName("skillLevel")[0].style.display = "block";
                document.getElementsByClassName("skillLevel")[1].style.display = "block";
            }
        }else if(value==='join'){
            modal.style.display="block";
            joinModal.style.display="block";
        }
        else if(value==='history'){
            if(guest){
                alert('LOCKED : Log in to unlock this');
                return;
            }
            window.location.href=x+"/watchHistory"+"/"+uniqueId;
        }
        else if(value==='pgnAnalyse'){
            document.getElementById("fileUpload").action=x+"/upload";
            modal.style.display="block";
            pgnAnalyse.style.display="block";
        }

    }
    close.onclick=function() {
        modal.style.display = "none";
        diffModal.style.display="none";
    }
    close1.onclick=function() {
        modal.style.display = "none";
        joinModal.style.display="none";
    }
    close2.onclick=function() {
        modal.style.display = "none";
        pgnAnalyse.style.display="none";
    }
    function myFunction() {
        var x = document.getElementById("myTopnav");
        if (x.className === "topnav") {
            x.className += " responsive";
        } else {
            x.className = "topnav";
        }
    }


    var slider = document.getElementById("skill");
     var output = document.getElementById("sliderValue");
output.innerHTML = slider.value;

slider.oninput = function() {
  output.innerHTML = this.value;
}

    // $(document).on("click",'body *',function () {


    //        if($(this)[0]!==document.getElementById('userId')){
    // var content=document.getElementsByClassName('dropdown-content')[0];
    //           if(content.style.display=="block"){
    //            content.style.display="none";
    //           }
    //        }
    //        console.log($(this));
    //    });

    // $('userId').click(function(){
    //       document.getElementsByClassName('dropdown-content')[0].style.display="block";
    // });


    // function dropDown() {
    //     var content=document.getElementById('dropdownContent');
    //     if(content.style.display==="block"){
    //         content.style.display="none";
    //     }else if(content.style.display==="none"){
    //         content.style.display="block";
    //     }
    // };


    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";   
                    diffModal.style.display="none";
        pgnAnalyse.style.display="none";
                joinModal.style.display="none";
        }
    }

    function clickResp(type, buttonNo) {
        if (type === 'standard') {
            baseTime = 15 * 60;
            extratime = 30;
            challengeType = 1;
        } else if (type === 'unlimited') {
            baseTime = 0;
            extratime = 0;
            challengeType = 2;
        } else if (type === 'lightning') {
            baseTime = 2 * 60;
            extratime = 10;
            challengeType = 3;
        } else if (type === 'blitz') {
            baseTime = 10 * 60;
            extratime = 0;
            challengeType = 4;
        } else if (type === 'custom') {
            baseTime = $('#baseTime').val() * 60;
            extratime = $("#extratime").val();
            challengeType = 5 + "&" + baseTime + "&" + extratime;
        }
    }

    function createGame() {


        playerColor = $("input[name='playerColor']:checked").val();

        skillLevel = document.getElementById('skill').value;


        if (extratime === null || extratime === "") {
            alert("Select a GameType")
            return false;
        }
        if (baseTime === null || baseTime === "") {
            alert("Select a GameType")
            return false;
        }
        if (mode === 'vsComp') {
            if (skillLevel === null || skillLevel === "" || skillLevel===undefined) {
                alert("Set a skill Level for AI")
                return false;
            }
        }
        if (playerColor === null || playerColor === undefined) {
            alert('Set your Side on Board')
            return false;
        }

        var x = window.location.origin;

        if (mode === 'vsComp') {
            $.ajax({
                url: x + "/diff",
                type: 'POST',
                data: {"difficulty": challengeType, "uniqueId": uniqueId},
                success: function (response) {
                    var gameId = response.gameId;
                    localStorage["skillLevel"]=skillLevel;
                    localStorage["baseTime"]=baseTime;
                    localStorage["extraTime"]=extratime;
                    localStorage["playerColor"]=playerColor;
                    document.location.href = x + "/vs?mode=computer&id=" + uniqueId + "&gameid=" + gameId ;                        console.log('difficulty ajax success!!');
                }
            });
        } else if (mode === 'multi') {
            $.ajax({
                url: x + "/createChallenge",
                type: 'POST',
                data: {"challengeType": challengeType, "uniqueId": uniqueId, "createdPlayAs": playerColor},
                success: function (response) {
                    var gameId = response.gameId;
                    var token = response.token;
                    document.location.href = x + "/waitingforopponent/" + token + "/" + gameId;
                    console.log('difficulty ajax success!!');
                }
            });
        }
    }

    function sendFileName() {
        var Pathfilename = document.getElementById('fileName').value;
        var filename = Pathfilename.split("\\").pop();
        var onlyFileName = filename.split(".")[0];
        var x = window.location.origin;
        $.ajax({
            url: x + "/setFileName",
            type: 'POST',
            data: {"fileName": onlyFileName},
            success: function (response) {
                console.log("fileName sent");
            }
        });
    }


    function joinGame() {
        var token = $('#token').val();
        $.ajax({
            url: x + "/joinGame",
            type: 'POST',
            data: {"tokenId": token, "uniqueId": uniqueId},
            success: function (response) {
                if (response.invalid === 'true') {
                    alert("The token is invalid")
                } else {
                    if (uniqueId === response.createdUser) {
                        playerColor = response.createdByPlayAs;
                    } else if (response.createdByPlayAs === 'w') {
                        playerColor = 'b';
                    } else if (response.createdByPlayAs === 'b') {
                        playerColor = 'w';
                    }
                    if (response.challengeType === '1') {
                        baseTime = 15 * 60;
                        extratime = 0;
                    } else if (response.challengeType === '2') {
                        baseTime = 0;
                        extratime = 0;
                    } else if (response.challengeType === '3') {
                        baseTime = 2 * 60;
                        extratime = 10;
                    } else if (response.challengeType === '4') {
                        baseTime = 10 * 60;
                        extratime = 30;
                    } else if (response.challengeType.startsWith("5")) {
                        baseTime = response.challengeType.split('&')[1];
                        extratime = response.challengeType.split('&')[2];
                    }
                    localStorage["baseTime"]=baseTime;
                    localStorage["extraTime"]=extratime;
                    localStorage["playerColor"]=playerColor;
                    location = "/vs?mode=multi&id=" + uniqueId + "&gameid=" + response.gameId + "&token=" + token;
                }
            }
        });
    }

    function resetForm() {
           document.getElementById('contact').reset();
           document.getElementById('message').innerHTML='';
    }


    function sendRedirect(goTo) {
        window.location.href=x+goTo
    }




jQuery(document).ready(function($) {




	'use strict';


      var owl = $("#owl-testimonials");

        owl.owlCarousel({
          
          pagination : true,
          paginationNumbers: false,
          autoPlay: 6000, //Set AutoPlay to 3 seconds
          items : 1, //10 items above 1000px browser width
          itemsDesktop : [1000,1], //5 items between 1000px and 901px
          itemsDesktopSmall : [900,1], // betweem 900px and 601px
          itemsTablet: [600,1], //2 items between 600 and 0
          itemsMobile : false // itemsMobile disabled - inherit from itemsTablet option
          
      });


        var top_header = $('.parallax-content');
        top_header.css({'background-position':'center center'}); // better use CSS

        $(window).scroll(function () {
        var st = $(this).scrollTop();
        top_header.css({'background-position':'center calc(50% + '+(st*.5)+'px)'});
        });


        $('.counter').each(function() {
          var $this = $(this),
              countTo = $this.attr('data-count');
          
          $({ countNum: $this.text()}).animate({
            countNum: countTo
          },

          {

            duration: 8000,
            easing:'linear',
            step: function() {
              $this.text(Math.floor(this.countNum));
            },
            complete: function() {
              $this.text(this.countNum);
              //alert('finished');
            }

          });  
          
        });


        $('.tabgroup > div').hide();
        $('.tabgroup > div:first-of-type').show();
        $('.tabs a').click(function(e){
          e.preventDefault();
            var $this = $(this),
            tabgroup = '#'+$this.parents('.tabs').data('tabgroup'),
            others = $this.closest('li').siblings().children('a'),
            target = $this.attr('href');
        others.removeClass('active');
        $this.addClass('active');
        $(tabgroup).children('div').hide();
        $(target).show();
      
        })



        $(".pop-button").click(function () {
            $(".pop").fadeIn(300);
            
        });

        $(".pop > span").click(function () {
            $(".pop").fadeOut(300);
        });


        $(window).on("scroll", function() {
            if($(window).scrollTop() > 100) {
                $(".header").addClass("active");
            } else {
                //remove the background property so it comes transparent again (defined in your css)
               $(".header").removeClass("active");
            }
        });


	/************** Mixitup (Filter Projects) *********************/
    	$('.projects-holder').mixitup({
            effects: ['fade','grayscale'],
            easing: 'snap',
            transitionSpeed: 400
        });



});
