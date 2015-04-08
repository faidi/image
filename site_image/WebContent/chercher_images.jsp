<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>


<body>
	<div>
		<h2>Choisissez une images depuis votre ordinateur et trouver des
			images similaires:</h2>
		<hr>
		<legend>

		<form method="post" action="find" enctype="multipart/form-data">
			<fieldset>
				  
				 <input type="file" name="image">
				<br>
				<input type="submit" value="Trouver">
			</fieldset>
		</form>
	</legend>
		 
	</div>
</body>
 
</html>