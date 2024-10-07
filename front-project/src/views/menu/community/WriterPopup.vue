<script setup>
const emit = defineEmits(['close']); // 부모 컴포넌트로 닫기 이벤트 전달

function closePopup() {
  emit('close');
}
</script>

<template>
  <div class="writer popup">
    <div class="popup-content">
      <!-- 이미지와 버튼을 좌우로 배치 -->
      <div class="header d-flex justify-content-between align-items-center">
        <img src="/img/imsi.png" />
        <button>작성</button>
      </div>

      <textarea placeholder="무슨 일이 일어나고 있나요?" rows="5"></textarea>
      <div class="filebox">
        <input type="file" id="file">
      </div>
      <div class="filebox">
        <input type="file" id="file">
      </div>
    </div>
  </div>
</template>

<style scoped>
.filebox input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.popup-content img {
  width: 70px;
  border-radius: 50%;
}

.writer {
  width: 449px;
  height: 300px;
  border: 1px solid lightgrey;
  border-radius: 30px;
}

.popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 500px;
  background-color: white;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  z-index: 100; /* 흐린 배경 위에 위치 */
}

.popup-content {
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

textarea {
  width: 100%;
  height: 180px;
  padding: 20px;
  margin-bottom: 10px;
  border-radius: 20px;
  border: none;
  margin-top: -30px;
  resize: none;
}

button {
  width: 100px;
  height: 40px;
  border-radius: 10px;
  margin-top: -30px;
  background-color: rgba(67, 140, 116, 1);
  color: white;
  border: none;
  cursor: pointer;
}

</style>
