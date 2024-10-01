<template>
  <div class="home">
    <Feature_1 />
    <br><br><br><br>
    <Feature_2 />
    <br><br>
    <Feature_3 />
    <br><br>
    <Feature_4 />
    <br><br>
    <Feature_5 />
    <br><br>
    <Feature_6 />
    <br><br>
    <Feature_7 />
    <br><br>
    <Feature_8 />
  </div>
</template>

<script>
import Feature_1 from '@/components/Feature_1.vue';
import Feature_2 from '@/components/Feature_2.vue';
import Feature_3 from '@/components/Feature_3.vue';
import Feature_4 from '@/components/Feature_4.vue';
import Feature_5 from '@/components/Feature_5.vue';
import Feature_6 from '@/components/Feature_6.vue';
import Feature_7 from '@/components/Feature_7.vue';
import Feature_8 from '@/components/Feature_8.vue';

export default {
  name: 'Home',
  components: {
    Feature_1,
    Feature_2,
    Feature_3,
    Feature_4,
    Feature_5,
    Feature_6,
    Feature_7,
    Feature_8
  }
}
</script>