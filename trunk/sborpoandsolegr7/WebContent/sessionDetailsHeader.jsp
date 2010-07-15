<%if ((request.getUserPrincipal()!=null)&&(request.getUserPrincipal().getName()!=null)){ %>
<p>
Logged in as: <b><%=request.getUserPrincipal().getName()%></b>&nbsp;&nbsp;&nbsp;&nbsp;<a href=viewUser.jsp?username=<%=request.getUserPrincipal().getName()%>>View Details</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="Logout">Log-Out</a>
</p>
<hr/>
<br/>
<br/>



<!-- ############### #### Navigation Bar #### ############### -->

<!-- Link to styles used for our Navigation Bar -->
<link href="/sborpoandsolegr7/css/SimpleNavBarStyles.css" rel="stylesheet" type="text/css">

<!-- Link to a file with couple simple JavaScript functions used for our Navigation Bar -->
<script src="/sborpoandsolegr7/js/SimpleNavBarScripts.js" language="JavaScript" type="text/javascript"></script>


<!-- main nav bar titles -->
<!-- Note how the the closing angle bracket of each </a> tag runs up against the next <a> tag,
      to avoid leaving a gap between each menu title and the next one. -->
      

<!-- REPLACE each "placeholder.html" URL below with the specific page you want the user
      to go to when the given menu title is clicked. For example, the first link below
      is for the "Home" menu title, so you'd probably replace the first URL with index.html. -->

<div class="mynavbar">

<a class="navbartitle" id="t1" href="/sborpoandsolegr7/index.jsp"  onMouseOver="ShowItem('main_submenu');" 
    onMouseOut="HideItem('main_submenu');">Main</a
><a class="navbartitle" id="t2" 
      onMouseOut="HideItem('users_submenu');" 
      onMouseOver="ShowItem('users_submenu');"
    >Users</a
><a class="navbartitle" id="t3" 
      onMouseOut="HideItem('reservations_submenu');" 
      onMouseOver="ShowItem('reservations_submenu');"
    >Reservations</a
><a class="navbartitle" id="t4" 
      onMouseOut="HideItem('instruments_submenu');" 
      onMouseOver="ShowItem('instruments_submenu');"
    >Instruments</a
>


<!-- ##################start of submenuts ################### -->

<div class="submenu" id="main_submenu" 
    onMouseOver="ShowItem('main_submenu');" 
    onMouseOut="HideItem('main_submenu');">
  <div class="submenubox">
    <ul>

      <li><a href="/sborpoandsolegr7/about.jsp" class="submenlink">About Us</a></li>
     </ul>

  </div>
</div>



<div class="submenu" id="users_submenu" 
    onMouseOver="ShowItem('users_submenu');" 
    onMouseOut="HideItem('users_submenu');">
  <div class="submenubox">
    <ul>
      <li><a href="/sborpoandsolegr7/viewUsers.jsp" class="submenlink">View users</a></li>
      </ul>
  </div>
</div>


<div class="submenu" id="reservations_submenu" 
    onMouseOver="ShowItem('reservations_submenu');" 
    onMouseOut="HideItem('reservations_submenu');">
  <div class="submenubox">
    <ul>
      <li><a href="/sborpoandsolegr7/viewReservations.jsp" class="submenlink">View time line</a></li>
       <li><a href="/sborpoandsolegr7/ws/searchInLabs.jsp" class="submenlink">Search for slots in other labs</a></li>
    </ul>
  </div>
</div>


<div class="submenu" id="instruments_submenu" 
    onMouseOver="ShowItem('instruments_submenu');" 
    onMouseOut="HideItem('instruments_submenu');">
  <div class="submenubox">
    <ul>

      <li><a href="placeholder.html" class="submenlink">Add links...</a></li>

     </ul>

  </div>
</div>


<!-- ################## end of submenuts ################### -->
</div>

<!-- ################## end of Navigation Bar ################### -->

<br/>
<br/>
<%} %>