# Stock Api



## Getting started


*** Thực hiện theo mô hình gitflow ***
### Việc tách nhánh thực hiện theo mô hình gitflow

**** 2 Nhánh chinh :


    - Master : Chứ code ổn định

    - Develop : Nhánh chính chứ code mới nhất

Nhánh hỗ trợ
Feature branches


    - Tách từ : develop

    - Merge vào : develop

    - Quy tắc đặt tên tự do trừ các tên có sẵn

    - Mỗi tính năng là một nhánh riêng tạo từ source mới nhất của devlop , sau khi dev xong merge lại vào develop

Release branches : chuẩn bị release cho bản production mới

    - Tách từ : develop

    - Merge vào : develop

    _ Đặt tên : release-*

    - Khi đã merge hết tính năng vào develop, tahcs ra release -> xử lý bug lẻ tẻ -> merge vào master -> Deloy code -> merge vào develop để các lần sau có những thay đổi này

Hotfix branches :

    - Tách từ : master

    - Merge vào master

    - Đặt tên : Hotfix-*

    - Tách từ master, đánh version đẻ nhận biết -> fix lỗi -> merge lại master -> merge vào develop để lần sau có code (Nếu đang có 1 release branch thì merge vào release branch này thay cho develop)

Xử lý conflict

Cách làm như sau:

    -	Nhánh tính năng là nhánh feature-1

    -	Nhánh code chính là nhánh develop (hoặc có thể là 1 nhánh release, hotfix bất kỳ)

    -	Chuyển sang nhánh develop và checkout code mới nhất về

    git checkout develop

    git pull

    -	Chuyển sang nhánh feature-1 và rebase với nó với nhánh develop.

    git checkout feature-1

    git rebase develop

    -	Kiểm tra và sửa code bị conflict. Nếu có nhiều commit bị conflict thì sau mỗi lần sửa conflict sẽ chạy tiếp git rebase –continue

    -	Đẩy code hoàn chỉnh lên nhánh feature-1

    git add file

    git rebase --continue

    git commit -m "id: Fix conflict file abc"

    git push origin feature-1

    -	Tạo Merge request vào nhánh develop

    -	Nếu trong quá trình rebase mà bị lỗi, bạn có thể hủy bằng cách

    git rebase --abort


