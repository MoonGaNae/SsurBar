<template>
  <div id="sidebar">
    <div id="sidebar-wrapper">
      <ul class="sidebar-nav nav-pills nav-stacked" id="menu">
        <li style="margin-top: 25vh; margin-left: 5%">
          <router-link to="/">
            <a href="#"
              ><i class="fas fa-home fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <router-link to="/form/createform">
            <a href="#"
              ><i class="fas fa-plus-square fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <router-link to="/">
            <a href="#"
              ><i class="fas fa-chart-bar fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <router-link to="/">
            <a href="#"
              ><i class="fas fa-cog fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>
      </ul>
    </div>
    <!-- <div style="background-color: rgb(5, 25, 58); height: 100vh">
      <div>네브바같은 느낌으로다가</div>
      <div
        style="
          background-position: center;
          background-color: white;
          margin-top: 5%;
          margin-left: 7%;
          margin-right: 7%;
          height: 90vh;
          border-radius: 60px 60px 0% 0%;
        "
      ></div>
    </div> -->
  </div>
</template>

<script>
export default {
  name: "Sidebar",
};
</script>

<style>
.nav-pills > li > a {
  border-radius: 0;
}

#sidebar {
  padding-left: 0;
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
  overflow: hidden;
}
#sidebar-wrapper {
  z-index: 1000;
  position: absolute;
  left: 100px;
  width: 0;
  height: 100%;
  margin-left: -100px;
  overflow-y: auto;
  background: white;
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
}

.sidebar-nav {
  position: absolute;
  top: 0;
  width: 100px;
  margin: 0;
  padding: 0;
  list-style: none;
  margin-top: 2px;
}

.sidebar-nav li {
  text-indent: 15px;
  line-height: 40px;
}

.sidebar-nav li a {
  display: block;
  text-decoration: none;
  color: #999999;
}

.sidebar-nav li a:hover {
  color: rgb(5, 25, 58);
  background: black;
  width: 8%;
}

@media (min-width: 768px) {
  #sidebar {
    padding-left: 100px;
  }
  .fixed-brand {
    width: 100px;
  }
  #sidebar.toggled {
    padding-left: 0;
  }
  #sidebar-wrapper {
    width: 100px;
  }
}
</style>
