<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajouter une image à la base de données</title>
</head>


<body>
<h5>Ajouter une image à la base de données</h5> 


<legend>

		<form method="post" action="upload" enctype="multipart/form-data">
			<fieldset>
				<label>Donner un nom a l'image 
				</label>
				 <input type="text" name="nom">  
				 <input type="file" name="image">
				<br>
				<input type="submit" value="Charger l'image">
			</fieldset>
		</form>
	</legend>
</body>
</html>