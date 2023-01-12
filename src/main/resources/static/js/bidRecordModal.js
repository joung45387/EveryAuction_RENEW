function newModal(tmp){
    var newFrame = document.createElement("iframe");
    newFrame.width="100%";
    newFrame.height="100%";
    newFrame.src="/item/"+tmp+"/bidRecord";

    newFrame.id="newFrame";
    let nf = document.getElementById("newFrame");
    nf.replaceWith(newFrame);
}

function newModal2(tmp){
    var newFrame = document.createElement("iframe");
    newFrame.width="100%";
    newFrame.height="100%";
    newFrame.src="/chat/"+tmp;

    newFrame.id="newFrame2";
    let nf = document.getElementById("newFrame2");
    nf.replaceWith(newFrame);
}