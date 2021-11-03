<template>
    <div class="col d-flex justify-content-center mb-5">
        <div class="shadow rounded text-center" style="width: 65%;">
            <div style="background-color:#F4F3F3;">
                <p class="fw-bold fs-2" >{{template.title}}</p>
            </div>
            <div class="fw-bolder p-5">
                {{template.description}}
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: "DefaultTemplateItem",
  props: ['template']
};
</script>
<style scoped>
.rounded {
  border-radius: 1rem !important; 
}
</style>