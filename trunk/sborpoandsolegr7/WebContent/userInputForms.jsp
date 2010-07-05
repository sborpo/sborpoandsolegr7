<%@page import="cs236369.hw5.users.*" %>
<%@page import="cs236369.hw5.*" %>
<%@page import="cs236369.hw5.User.*"%>
<%
	String des=request.getParameter("decision");
User user=null;
String login=null;
boolean isAdmin= UserUtils.isAdmin(request);
if (des.equals("update")){login = request.getParameter("username");  user=UserManager.getUserDetails(login); }
%>
<table> 
			
			 <tr> 
	  			<td class="label"><label id="lusername" for="username">Username</label></td> 
	  			<td class="field"><input id="username" type="text"   maxlength="50" <%if (!des.equals("add")){%> readonly="readonly" value="<%=login%>" <%}%> name="<%=UserManager.Usern%>" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  		  	<td class="label"><label id="lname" for="name">Name</label></td> 
	  		  	<td class="field"><input id="name"  type="text" value="<%=(user!=null)? user.getName() : ""%>" maxlength="100" name="<%=UserManager.Name%>" /></td> 
	  		  	<td class="status"></td> 
	  		  </tr>
	  		  <%
	  		  	if (des.equals("add")){
	  		  %> 
	  		  <tr> 
	  			<td class="label"><label id="lpassword" for="password">Password</label></td> 
	  			<td class="field"><input id="password" type="password" maxlength="50" value="" name="<%=UserManager.Password%>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		  <tr> 
	  			<td class="label"><label id="lpassword_confirm" for="password_confirm">Confirm Password</label></td> 
	  			<td class="field"><input id="password_confirm" type="password" maxlength="50" value="" name="<%=UserManager.PassConfirm%>"/></td> 
	  			<td class="status"></td> 
	  		  </tr> 
	  		    <%
 	  		    	}
 	  		    %>
 	  		  <tr> 
	  			<td class="label"><label id="lemail" for="email">Address</label></td> 
	  			<td class="field"><input id="email" name="<%=UserManager.Email%>" type="text" value="<%=(user!=null)? user.getAddress() : ""%>" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr>
	  		  <tr> 
	  			<td class="label"><label id="laddress" for="address">Address</label></td> 
	  			<td class="field"><input id="address" name="<%=UserManager.Address%>" type="text" value="<%=(user!=null)? user.getAddress() : ""%>" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			    <tr> 
	  			<td class="label"><label id="lphoneno" for="phoneno">Phone Number</label></td> 
	  			<td class="field"><input id="phoneno" name="<%=UserManager.PhoneNumber%>" type="text" value="<%=(user!=null)? user.getPhoneNumber() : ""%>" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr> 
			  <tr> 
	  			<td class="label"><label id="lgroup" for="group">Group</label></td> 
	  			<td class="field"><input id="group" name="<%=UserManager.Group%>" type="text" value="<%=(user!=null)? user.getGroup() : ""%>" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr>
	  		  <%
	  		  	if (isAdmin){
	  		  %>
	  		    <tr> 
	  			<td class="label"><label id="lperm" for="permission">Permissions<br/>(seperated by commas)</label></td> 
	  			<td class="field"><input id="perm" name="<%=UserManager.Permission%>" type="text" value="<%=(user!=null)? user.getPremissions() : ""%>" maxlength="150" /></td> 
	  			<td class="status"></td> 
	  		  </tr>  
	  		  <%
  	  		  	}
  	  		  %>
			  <tr> 
	  			<td class="label"><label id="lusertype" for="usertype">User Type</label></td>
				<%
					if ((user!=null)&&((user.getRole()).equals(UserType.ADMIN))){
				%> 
	  			<td class="field"><select id="usertype" name="<%=UserManager.UserTypen%>">
						<option value="Researcher" >Researcher</option>
						<option value="Admin" selected="selected">Administrator</option>
					</select>
				</td>
				<%
					} else{
				%>
				<td class="field"><select id="usertype" name="<%=UserManager.UserTypen%>">
						<option value="Researcher" selected="selected">Researcher</option>
						<option value="Admin">Administrator</option>
					</select>
				</td>
				<%
					}
				%>
	  			<td class="status"></td> 
	  		  </tr> 
 			  <tr>
 			  	<td class="label"><label id="lpicture" for="picture">Picture (Max Size: 300 Kb):</label></td>
 			  	<td class="field"><input id="picture" type="hidden" name="MAX_FILE_SIZE" value="<%=UserManager.FileSizeInBytes/1000%>" />
								  <input type="file" name="userpicture" />
				</td>
			  </tr>
			  <%
			  	if ((user!=null) && (user.getPhoto()!=null)){
			  %>
			   <tr>
			  	<td class="label"><label>user photo</label></td><td class="field"><img src="ImageGetter?username=<%=(user!=null)? user.getLogin(): "stam"%>" /><br/></td>
			  </tr>
			<%
				}
			%>
			
			  <tr>
			  <td class="label"><label id="lcaptcha" for="captcha">Enter the letters below:</label></td>
			  	<td class="field"><input id="captcha" type="text" name="<%=UserManager.Captcha%>" value="" />	</td>

			  </tr>
			    <tr>
			  	<td></td><td ><img src="jcaptcha.jpg" /></td>
			  </tr>
		
		
			 
		
			   

</table>