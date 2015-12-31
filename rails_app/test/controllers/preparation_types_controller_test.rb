require 'test_helper'

class PreparationTypesControllerTest < ActionController::TestCase
  setup do
    @preparation_type = preparation_types(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:preparation_types)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create preparation_type" do
    assert_difference('PreparationType.count') do
      post :create, preparation_type: { name: @preparation_type.name }
    end

    assert_redirected_to preparation_type_path(assigns(:preparation_type))
  end

  test "should show preparation_type" do
    get :show, id: @preparation_type
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @preparation_type
    assert_response :success
  end

  test "should update preparation_type" do
    patch :update, id: @preparation_type, preparation_type: { name: @preparation_type.name }
    assert_redirected_to preparation_type_path(assigns(:preparation_type))
  end

  test "should destroy preparation_type" do
    assert_difference('PreparationType.count', -1) do
      delete :destroy, id: @preparation_type
    end

    assert_redirected_to preparation_types_path
  end
end
